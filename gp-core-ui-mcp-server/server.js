#!/usr/bin/env node
const { Server } = require('@modelcontextprotocol/sdk/server/index.js');
const { StdioServerTransport } = require('@modelcontextprotocol/sdk/server/stdio.js');
const path = require('path');
const ComponentParser = require('./lib/component-parser');
const CodeGenerator = require('./lib/code-generator');
const Validators = require('./lib/validators');

console.error('STARTING GP CORE UI MCP SERVER');

const server = new Server(
  { name: 'gp-coreui-mcp-server', version: '0.1.0' },
  { capabilities: { tools: {} } }
);

const docsPath = process.env.DOCS_PATH || path.join(__dirname, 'docs');
const parser = new ComponentParser(docsPath);
parser.loadComponents();
const generator = new CodeGenerator();
const validators = new Validators();

const TOOLS = [
  {
    name: 'get_component_list',
    description: 'Returns all available GP Core UI components',
    inputSchema: { type: 'object', properties: {}, required: [] }
  },
  {
    name: 'get_component_docs',
    description: 'Get full documentation for a component',
    inputSchema: {
      type: 'object',
      properties: {
        componentName: { type: 'string', description: 'Component name' }
      },
      required: ['componentName']
    }
  },
  {
    name: 'generate_xml_usage',
    description: 'Generate XML usage example',
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
    description: 'Generate Java usage example',
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
    description: 'Validate component usage code',
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
    description: 'Get step-by-step integration guide',
    inputSchema: {
      type: 'object',
      properties: { scenario: { type: 'string' } },
      required: ['scenario']
    }
  }
];

server.fallbackRequestHandler = async (request) => {
  try {
    const { method, params, id } = request;
    console.error(`REQUEST: ${method} [${id}]`);

    if (method === 'initialize') {
      return {
        protocolVersion: '2024-11-05',
        capabilities: { tools: {} },
        serverInfo: { name: 'gp-coreui-mcp-server', version: '0.1.0' }
      };
    }

    if (method === 'tools/list') {
      return { tools: TOOLS };
    }

    if (method === 'tools/call') {
      const { name, arguments: args = {} } = params || {};

      switch (name) {
        case 'get_component_list':
          return { content: [{ type: 'json', json: parser.getAllComponents() }] };
        case 'get_component_docs':
          return { content: [{ type: 'json', json: parser.getComponent(args.componentName) }] };
        case 'generate_xml_usage':
          return { content: [{ type: 'text', text: generator.generateXMLUsage(args.componentName, args.attributes) }] };
        case 'generate_java_usage':
          return { content: [{ type: 'text', text: generator.generateJavaUsage(args.componentName, args.variableName, args.includeListeners) }] };
        case 'validate_component_usage':
          if (args.language === 'xml') {
            return { content: [{ type: 'json', json: validators.validateXMLUsage(args.componentName, args.code) }] };
          }
          if (args.language === 'java') {
            return { content: [{ type: 'json', json: validators.validateJavaUsage(args.componentName, args.code) }] };
          }
          return { error: { code: -32602, message: 'Invalid language' } };
        case 'search_components':
          return { content: [{ type: 'json', json: parser.searchComponents(args.query, args.category) }] };
        case 'get_integration_guide':
          return { content: [{ type: 'json', json: generator.getIntegrationGuide(args.scenario) }] };
        case 'about':
          return { content: [{ type: 'text', text: 'GP Core UI MCP server' }] };
        case 'hello':
          return { content: [{ type: 'text', text: `Hello, ${args.name || 'World'}!` }] };
        default:
          return { error: { code: -32601, message: `Tool not found: ${name}` } };
      }
    }

    if (method === 'resources/list') return { resources: [] };
    if (method === 'prompts/list') return { prompts: [] };

    return {};
  } catch (error) {
    console.error(`ERROR: ${error.message}`);
    return { error: { code: -32603, message: 'Internal error', data: { details: error.message } } };
  }
};

const transport = new StdioServerTransport();

process.on('SIGTERM', () => {
  console.error('SIGTERM received but staying alive');
});

server.connect(transport)
  .then(() => console.error('Server connected'))
  .catch(error => {
    console.error(`Connection error: ${error.message}`);
    process.exit(1);
  });
