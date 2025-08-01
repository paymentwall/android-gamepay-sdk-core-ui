# GP Core UI MCP Server

This server exposes Model Context Protocol tools for the GP Core UI library. It provides AI assistants with knowledge about UI components and helps generate code snippets, documentation, and implementation guidance.

## Quick Start

### 1. Install Dependencies
```bash
npm install
```

### 2. Start the Server
```bash
npm start
```

The server will start and display the URL you need to add to Cursor.

### 3. Add to Cursor

1. **Open Cursor**
2. **Go to Tools → Add Local MCP Server**
3. **Enter the server URL**: `http://localhost:3000`
4. **Save and restart Cursor**

## Server Information

- **Server URL**: `http://localhost:3000`
- **Server Name**: `gp-coreui-mcp-server`
- **Version**: `0.1.0`
- **Port**: `3000` (configurable via `PORT` environment variable)

## Available Tools

- `get_component_list` - Returns all available GP Core UI components
- `get_component_docs` - Get full documentation for a component
- `generate_xml_usage` - Generate XML usage example
- `generate_java_usage` - Generate Java usage example
- `validate_component_usage` - Validate component usage code
- `search_components` - Search components by query and category
- `get_integration_guide` - Get step-by-step integration guide

## Usage Examples

Once added to Cursor, you can ask questions like:
- "Show me all card-related components"
- "How do I use GPCardNumberField in XML?"
- "Generate a complete payment form with validation"
- "What parameters does GPDefaultInputContainer accept?"

## Development

### Environment Variables
- `PORT` - Server port (default: 3000)
- `DOCS_PATH` - Path to documentation files

### Testing
```bash
npm test
```

## Troubleshooting

1. **Server won't start**: Check if port 3000 is available
2. **Cursor can't connect**: Ensure the server is running and the URL is correct
3. **Tools not working**: Check the browser at `http://localhost:3000` to verify server status

## Server Status

Visit `http://localhost:3000` in your browser to see the server status page.
