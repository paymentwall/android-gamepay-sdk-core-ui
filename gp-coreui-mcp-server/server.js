#!/usr/bin/env node
const path = require('path');
const { Server } = require('@modelcontextprotocol/sdk/server/index.js');
const { StdioServerTransport } = require('@modelcontextprotocol/sdk/server/stdio.js');
const ComponentParser = require('./lib/component-parser');
const CodeGenerator = require('./lib/code-generator');
const ComponentValidator = require('./lib/validators');

const DOCS_PATH = process.env.DOCS_PATH || path.join(__dirname, 'docs');
const parser = new ComponentParser(DOCS_PATH);
const generator = new CodeGenerator(parser);
const validator = new ComponentValidator(parser);

const server = new Server({ name: 'gp-coreui-mcp-server', version: '1.0.0' }, { capabilities: { tools: {} } });

const TOOLS = [
  {
    name: 'get_component_list',
    description: 'List all available GP Core UI components',
    inputSchema: { type: 'object', properties: {}, required: [] }
  },
  {
    name: 'get_component_docs',
    description: 'Get documentation for a component',
    inputSchema: {
      type: 'object',
      properties: { componentName: { type: 'string' } },
      required: ['componentName']
    }
  },
  {
    name: 'generate_xml_usage',
    description: 'Generate XML usage snippet',
    inputSchema: {
      type: 'object',
      properties: {
        componentName: { type: 'string' },
        attributes: { type: 'object' }
      },
      required: ['componentName']
    }
  },
  {
    name: 'generate_java_usage',
    description: 'Generate Java usage snippet',
    inputSchema: {
      type: 'object',
      properties: {
        componentName: { type: 'string' },
        variableName: { type: 'string' },
        includeListeners: { type: 'boolean' }
      },
      required: ['componentName']
    }
  },
  {
    name: 'validate_component_usage',
    description: 'Validate code snippet for a component',
    inputSchema: {
      type: 'object',
      properties: {
        componentName: { type: 'string' },
        code: { type: 'string' },
        language: { type: 'string', enum: ['xml', 'java'] }
      },
      required: ['componentName', 'code', 'language']
    }
  },
  {
    name: 'search_components',
    description: 'Search components by query and category',
    inputSchema: {
      type: 'object',
      properties: {
        query: { type: 'string' },
        category: { type: 'string' }
      },
      required: ['query']
    }
  },
  {
    name: 'get_integration_guide',
    description: 'Get an integration guide for common scenarios',
    inputSchema: {
      type: 'object',
      properties: { scenario: { type: 'string' } },
      required: ['scenario']
    }
  }
];

server.fallbackRequestHandler = async (request) => {
  const { method, params, id } = request;
  if (method === 'initialize') {
    return { protocolVersion: '2024-11-05', capabilities: { tools: {} }, serverInfo: { name: 'gp-coreui-mcp-server', version: '1.0.0' } };
  }
  if (method === 'tools/list') {
    return { tools: TOOLS };
  }
  if (method === 'tools/call') {
    const { name, arguments: args = {} } = params || {};
    try {
      switch (name) {
        case 'get_component_list':
          return { content: [{ type: 'json', json: parser.getAllComponents().map(c => c.name) }] };
        case 'get_component_docs':
          return { content: [{ type: 'json', json: parser.getComponent(args.componentName) }] };
        case 'generate_xml_usage':
          return { content: [{ type: 'code', language: 'xml', text: generator.generateXMLUsage(args.componentName, args.attributes) }] };
        case 'generate_java_usage':
          return { content: [{ type: 'code', language: 'java', text: generator.generateJavaUsage(args.componentName, args.variableName, args.includeListeners) }] };
        case 'validate_component_usage':
          if (args.language === 'xml') {
            return { content: [{ type: 'json', json: validator.validateXMLUsage(args.componentName, args.code) }] };
          } else {
            return { content: [{ type: 'json', json: validator.validateJavaUsage(args.componentName, args.code) }] };
          }
        case 'search_components':
          return { content: [{ type: 'json', json: parser.searchComponents(args.query, args.category) }] };
        case 'get_integration_guide':
          return { content: [{ type: 'text', text: generator.getIntegrationGuide(args.scenario) }] };
        default:
          return { error: { code: -32601, message: `Tool not found: ${name}` } };
      }
    } catch (err) {
      return { error: { code: -32603, message: err.message } };
    }
  }
  if (method === 'resources/list') return { resources: [] };
  if (method === 'prompts/list') return { prompts: [] };
  return {};
};

const transport = new StdioServerTransport();
server.connect(transport).catch(err => {
  console.error('Connection error', err);
  process.exit(1);
});
