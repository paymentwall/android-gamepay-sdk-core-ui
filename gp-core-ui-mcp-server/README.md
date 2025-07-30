# GP Core UI MCP Server

This server exposes Model Context Protocol tools for the GP Core UI library. It is based on the MCP JS Server Template.

## Installation

```bash
npm install
```

## Claude Desktop Configuration

Configure Claude Desktop to run `server.js` via Node.

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
