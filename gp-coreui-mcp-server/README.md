# GP Core UI MCP Server

This server exposes Model Context Protocol tools for the GP Core UI Android library.
It is based on the [MCP JS Server Template](https://github.com/ryaker/MCP-JS-Server-Template).

## Setup

```
npm install
```

Environment variables:
- `DOCS_PATH` – path to the documentation folder (default `./docs`)

## Tools
- `get_component_list`
- `get_component_docs`
- `generate_xml_usage`
- `generate_java_usage`
- `validate_component_usage`
- `search_components`
- `get_integration_guide`

Run the server with:

```
node server.js
```
