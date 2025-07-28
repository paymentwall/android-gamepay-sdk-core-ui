# MCP Server Instructions for GP Core UI Library

## Overview
Create an MCP (Model Context Protocol) server for the GP Core UI payment SDK library using Node.js and JavaScript. The server will provide AI assistants with knowledge about the UI components and help generate code snippets, documentation, and implementation guidance.

## Project Structure
Based on the MCP-JS-Server-Template from https://github.com/ryaker/MCP-JS-Server-Template

```
gp-coreui-mcp-server/
├── package.json
├── server.js
├── README.md
├── docs/
│   ├── COMPONENTS.md
│   └── components/
│       ├── GPDefaultInputContainer.md
│       ├── GPDefaultEditText.md
│       ├── GPCardNumberField.md
│       ├── GPCardExpiryDateField.md
│       ├── GPCardCVVField.md
│       └── GPDropdown.md
└── lib/
    ├── component-parser.js
    ├── code-generator.js
    └── validators.js
```

## Requirements

### Dependencies
- Node.js (v16 or later)
- `@modelcontextprotocol/sdk`
- `fs` and `path` (built-in)

### Installation
```bash
npm init -y
npm install @modelcontextprotocol/sdk
```

## MCP Server Implementation

### 1. Main Server File (server.js)

Create the main MCP server with the following tools:

#### Core Tools to Implement:

1. **`get_component_list`**
   - Returns list of all available GP Core UI components
   - No parameters required

2. **`get_component_docs`**
   - Parameter: `componentName` (string) - Name of the component (e.g., "GPCardNumberField")
   - Returns full documentation for the specified component

3. **`generate_xml_usage`**
   - Parameters: 
     - `componentName` (string) - Component name
     - `attributes` (object, optional) - Key-value pairs for XML attributes
   - Generates XML usage example

4. **`generate_java_usage`**
   - Parameters:
     - `componentName` (string) - Component name
     - `variableName` (string, optional) - Variable name to use
     - `includeListeners` (boolean, optional) - Whether to include event listeners
   - Generates Java/Kotlin usage example

5. **`validate_component_usage`**
   - Parameters:
     - `componentName` (string) - Component name
     - `code` (string) - Code snippet to validate
     - `language` (string) - "xml" or "java"
   - Validates if the code follows proper component usage patterns

6. **`search_components`**
   - Parameters:
     - `query` (string) - Search term
     - `category` (string, optional) - "card", "input", "dropdown", etc.
   - Returns components matching the search criteria

7. **`get_integration_guide`**
   - Parameters:
     - `scenario` (string) - "payment_form", "card_input", "basic_form", etc.
   - Returns step-by-step integration guide for common scenarios

### 2. Component Parser (lib/component-parser.js)

Create a module to parse and extract information from component documentation:

```javascript
class ComponentParser {
  constructor(docsPath) {
    this.docsPath = docsPath;
    this.components = new Map();
    this.loadComponents();
  }
  
  loadComponents() {
    // Load and parse all component markdown files
    // Extract: name, description, parameters, usage examples
  }
  
  getComponent(name) {
    // Return parsed component data
  }
  
  getAllComponents() {
    // Return all components
  }
  
  searchComponents(query, category) {
    // Search functionality
  }
}
```

### 3. Code Generator (lib/code-generator.js)

Create a module for generating code snippets:

```javascript
class CodeGenerator {
  generateXMLUsage(componentName, attributes = {}) {
    // Generate XML usage example
    // Handle inheritance from GPDefaultInputContainer
  }
  
  generateJavaUsage(componentName, options = {}) {
    // Generate Java usage example
    // Include findViewById, getters, setters, listeners
  }
  
  generateIntegrationGuide(scenario) {
    // Generate step-by-step guides for common scenarios
  }
}
```

### 4. Validators (lib/validators.js)

Create validation utilities:

```javascript
class ComponentValidator {
  validateXMLUsage(componentName, code) {
    // Validate XML attributes and structure
    // Check for required attributes
    // Validate inheritance chain
  }
  
  validateJavaUsage(componentName, code) {
    // Validate Java method calls
    // Check for proper getter/setter usage
  }
}
```

## Component Data Structure

Each component should be parsed into this structure:

```javascript
{
  name: "GPCardNumberField",
  package: "com.terminal3.gpcoreui.components",
  inheritsFrom: "GPDefaultInputContainer",
  description: "Extends GPDefaultInputContainer to provide credit card number input...",
  parameters: {
    // Inherited and own parameters
    labelText: { type: "string", description: "text shown above the field" },
    text: { type: "string", description: "initial text value" },
    // ... other parameters
  },
  methods: {
    getCardNumber: { 
      returnType: "String", 
      description: "returns digits without spaces" 
    },
    // ... other methods
  },
  xmlExample: "...",
  javaExample: "...",
  category: "card" // card, input, dropdown, etc.
}
```

## Integration Scenarios

Provide guides for these common scenarios:

1. **Basic Payment Form**
   - Card number, expiry, CVV fields
   - Form validation
   - Error handling

2. **Card Input Only**
   - Just card number field
   - Brand detection

3. **Complete Payment Form**
   - All card fields + dropdown for country
   - Full validation chain

4. **Custom Input Container**
   - Using GPDefaultInputContainer
   - Custom styling and behavior

## Error Handling

Implement comprehensive error handling for:
- Invalid component names
- Missing documentation files
- Malformed markdown
- Invalid code validation requests

## Server Configuration

The server should be configurable via environment variables:
- `DOCS_PATH` - Path to documentation files
- `LOG_LEVEL` - Logging level
- `PORT` - Server port (if needed)

## Testing Strategy

Create test cases for:
- Component parsing accuracy
- Code generation correctness
- Validation logic
- Search functionality
- Integration scenarios

## Usage Examples

After implementation, the MCP server should support queries like:

- "Show me all card-related components"
- "How do I use GPCardNumberField in XML?"
- "Generate a complete payment form with validation"
- "What parameters does GPDefaultInputContainer accept?"
- "Validate this XML usage of GPCardCVVField"

## Documentation

Include comprehensive README.md with:
- Installation instructions
- Claude Desktop configuration
- Available tools and parameters
- Usage examples
- Troubleshooting guide

## Deployment

Instructions for:
1. Local development setup
2. Claude Desktop integration
3. Configuration management
4. Updating component documentation

This MCP server will serve as a knowledgeable assistant for developers working with the GP Core UI library, providing instant access to documentation, code generation, and validation capabilities.
