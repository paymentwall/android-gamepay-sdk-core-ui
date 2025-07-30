# GP Core UI MCP Server

This server exposes Model Context Protocol tools for the GP Core UI library. It is based on the MCP JS Server Template.

## Installation

```bash
npm install
```

## Claude Desktop Configuration

Configure Claude Desktop to run `server.js` via Node.

### Setup with Claude Code and Cursor

Follow these steps to use the MCP server with the Claude Code extension or the
Cursor editor:

1. **Install dependencies**
   ```bash
   npm install
   ```
2. **Start the server**
   ```bash
   npm start
   ```
3. **Add the server in Claude Code**
   - Open *Claude Code* and navigate to **Settings → MCP Servers**.
   - Click **Add Local Server** and enter `node <path-to-server>/server.js`.
   - Save the configuration and restart Claude Code if prompted.
4. **Add the server in Cursor**
   - Open *Cursor* and go to **Tools → Add Local MCP Server**.
   - Provide the command `node <path-to-server>/server.js`.
   - Save and enable the server in the tools panel.

## Available Tools

- `get_component_list`
- `get_component_docs`
- `generate_xml_usage`
- `generate_java_usage`
- `validate_component_usage`
- `search_components`
- `get_integration_guide`

## Usage Examples

```
get_component_list
```

## Development Setup

Ensure Node.js 16+ is installed and run tests with `npm test`.

## Testing

Placeholder tests exist under `tests/`.

## Troubleshooting

Check environment variables `DOCS_PATH`, `PORT`, and `LOG_LEVEL`.
