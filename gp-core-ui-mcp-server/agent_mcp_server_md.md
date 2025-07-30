# AGENT_MCP_SERVER.md - AI Coding Agent Instructions for MCP Server

## Project Overview

Create an **MCP (Model Context Protocol) server** for the GP Core UI payment SDK library using Node.js and JavaScript. The server provides AI assistants with knowledge about UI components and helps generate code snippets, documentation, and implementation guidance.

### Base Template
Use the MCP-JS-Server-Template from: https://github.com/ryaker/MCP-JS-Server-Template

### Key Technologies
- **Language**: Node.js with JavaScript
- **Framework**: Model Context Protocol SDK
- **Target**: Claude Desktop integration
- **Purpose**: GP Core UI library assistant

## Branch Naming Convention

**CRITICAL**: All branches MUST follow this exact naming pattern:
```
YYMMMDD_Number_task_name
```

### Examples:
- `25Jul30_001_init_mcp_server`
- `25Jul31_002_add_component_parser`
- `25Aug01_003_implement_code_generator`
- `25Aug02_004_add_validation_tools`

## Project Folder Structure

```
gp-coreui-mcp-server/
├── package.json                        # Dependencies and scripts
├── server.js                          # Main MCP server implementation
├── README.md                          # Installation and usage guide
├── .env.example                       # Environment variables template
├── docs/                              # Component documentation
│   ├── COMPONENTS.md                  # Component index
│   └── components/                    # Individual component docs
│       ├── GPDefaultInputContainer.md
│       ├── GPDefaultEditText.md
│       ├── GPCardNumberField.md
│       ├── GPCardExpiryDateField.md
│       ├── GPCardCVVField.md
│       └── GPDropdown.md
├── lib/                               # Core modules
│   ├── component-parser.js            # Documentation parser
│   ├── code-generator.js              # Code snippet generator
│   └── validators.js                  # Code validation utilities
└── tests/                             # Test files
    ├── component-parser.test.js
    ├── code-generator.test.js
    └── validators.test.js
```

## Core Components to Implement

### 1. Main Server (server.js)

#### Required MCP Tools:

1. **`get_component_list`**
   - **Purpose**: Returns all available GP Core UI components
   - **Parameters**: None
   - **Returns**: Array of component objects with name, description, category

2. **`get_component_docs`**
   - **Purpose**: Get full documentation for specific component
   - **Parameters**: `componentName` (string)
   - **Returns**: Complete component documentation object

3. **`generate_xml_usage`**
   - **Purpose**: Generate XML usage example
   - **Parameters**: 
     - `componentName` (string) - Required
     - `attributes` (object, optional) - Key-value pairs
   - **Returns**: Formatted XML code snippet

4. **`generate_java_usage`**
   - **Purpose**: Generate Java usage example
   - **Parameters**:
     - `componentName` (string) - Required
     - `variableName` (string, optional) - Default: "component"
     - `includeListeners` (boolean, optional) - Default: false
   - **Returns**: Formatted Java code snippet

5. **`validate_component_usage`**
   - **Purpose**: Validate code follows GP component patterns
   - **Parameters**:
     - `componentName` (string) - Required
     - `code` (string) - Code to validate
     - `language` (string) - "xml" or "java"
   - **Returns**: Validation result with errors/warnings

6. **`search_components`**
   - **Purpose**: Search components by query and category
   - **Parameters**:
     - `query` (string) - Search term
     - `category` (string, optional) - "card", "input", "dropdown"
   - **Returns**: Filtered component list

7. **`get_integration_guide`**
   - **Purpose**: Get step-by-step integration guide
   - **Parameters**: `scenario` (string) - "payment_form", "card_input", "basic_form"
   - **Returns**: Detailed integration steps

### 2. Component Parser (lib/component-parser.js)

#### Class Structure with #regions:

```javascript
class ComponentParser {
    //#region Fields and Constants
    constructor(docsPath) {
        this.docsPath = docsPath;
        this.components = new Map();
        this.categories = new Set();
    }
    //#endregion

    //#region Initialization Methods
    async loadComponents() {
        // Load and parse all component markdown files
        // Extract: name, description, parameters, usage examples
        // Build inheritance hierarchy
    }

    parseComponentFile(filePath) {
        // Parse individual markdown file
        // Extract structured data
    }
    //#endregion

    //#region Public API Methods
    getComponent(name) {
        // Return parsed component data with inheritance
    }

    getAllComponents() {
        // Return all components with categories
    }

    searchComponents(query, category) {
        // Fuzzy search functionality
    }

    getComponentsByCategory(category) {
        // Filter by category
    }
    //#endregion

    //#region Utility Methods
    buildInheritanceChain(componentName) {
        // Build parameter inheritance chain
    }

    extractParametersFromMarkdown(content) {
        // Parse parameter tables from markdown
    }
    //#endregion
}
```

### 3. Code Generator (lib/code-generator.js)

#### Class Structure with #regions:

```javascript
class CodeGenerator {
    //#region Fields and Constants
    constructor(componentParser) {
        this.componentParser = componentParser;
        this.xmlTemplates = new Map();
        this.javaTemplates = new Map();
    }
    //#endregion

    //#region XML Generation Methods
    generateXMLUsage(componentName, attributes = {}) {
        // Generate XML with proper namespace
        // Handle inheritance from GPDefaultInputContainer
        // Apply formatting and indentation
    }

    buildXMLAttributes(component, customAttributes) {
        // Merge inherited and custom attributes
        // Format as XML attributes
    }
    //#endregion

    //#region Java Generation Methods
    generateJavaUsage(componentName, options = {}) {
        // Generate findViewById
        // Generate getters, setters
        // Optionally include listeners
        // Proper GP component method calls
    }

    generateJavaSetters(component, variableName) {
        // Generate setter method calls
    }

    generateJavaListeners(component, variableName) {
        // Generate event listener setup
    }
    //#endregion

    //#region Integration Guide Methods
    generateIntegrationGuide(scenario) {
        // Return step-by-step implementation guide
        // Include code examples for each step
    }

    getPaymentFormGuide() {
        // Complete payment form implementation
    }

    getCardInputGuide() {
        // Single card input implementation
    }
    //#endregion

    //#region Utility Methods
    formatCode(code, language) {
        // Apply proper indentation and formatting
    }

    addCodeComments(code, component) {
        // Add helpful comments to generated code
    }
    //#endregion
}
```

### 4. Validators (lib/validators.js)

#### Class Structure with #regions:

```javascript
class ComponentValidator {
    //#region Fields and Constants
    constructor(componentParser) {
        this.componentParser = componentParser;
        this.xmlPatterns = new Map();
        this.javaPatterns = new Map();
    }
    //#endregion

    //#region XML Validation Methods
    validateXMLUsage(componentName, code) {
        // Check XML structure
        // Validate required attributes
        // Check namespace usage
        // Verify inheritance chain compliance
    }

    checkRequiredXMLAttributes(component, xmlNode) {
        // Validate required attributes are present
    }

    validateXMLNamespace(code) {
        // Check app: namespace usage
    }
    //#endregion

    //#region Java Validation Methods
    validateJavaUsage(componentName, code) {
        // Check method calls
        // Validate findViewById usage
        // Check getter/setter patterns
        // Verify GP component conventions
    }

    checkJavaMethodCalls(component, code) {
        // Validate method calls against component API
    }

    validateVariableNaming(code) {
        // Check GP naming conventions
    }
    //#endregion

    //#region Validation Result Methods
    createValidationResult(isValid, errors = [], warnings = []) {
        // Return structured validation result
    }

    formatValidationMessage(type, message, line) {
        // Format error/warning messages
    }
    //#endregion
}
```

## Required Dependencies

### package.json

```json
{
  "name": "gp-coreui-mcp-server",
  "version": "1.0.0",
  "description": "MCP server for GP Core UI library",
  "main": "server.js",
  "dependencies": {
    "@modelcontextprotocol/sdk": "^latest",
    "markdown-parser": "^latest",
    "xml-parser": "^latest"
  },
  "devDependencies": {
    "jest": "^latest",
    "nodemon": "^latest"
  },
  "scripts": {
    "start": "node server.js",
    "dev": "nodemon server.js",
    "test": "jest"
  }
}
```

## Component Data Structure

### Standard Component Object:

```javascript
{
  name: "GPCardNumberField",
  package: "com.terminal3.gpcoreui.components",
  inheritsFrom: "GPDefaultInputContainer",
  description: "Extends GPDefaultInputContainer to provide credit card number input with validation and formatting",
  category: "card",
  parameters: {
    // Inherited parameters
    labelText: { 
      type: "string", 
      description: "Text shown above the field",
      inherited: true,
      source: "GPDefaultInputContainer"
    },
    text: { 
      type: "string", 
      description: "Initial text value",
      inherited: true,
      source: "GPDefaultInputContainer"
    },
    // Own parameters
    cardBrandDetection: {
      type: "boolean",
      description: "Enable automatic card brand detection",
      default: "true",
      inherited: false
    }
  },
  methods: {
    getCardNumber: { 
      returnType: "String", 
      description: "Returns card number digits without spaces",
      parameters: []
    },
    setCardNumber: {
      returnType: "void",
      description: "Sets the card number value",
      parameters: [{ name: "cardNumber", type: "String" }]
    }
  },
  xmlExample: "<!-- Generated XML example -->",
  javaExample: "// Generated Java example",
  validationRules: {
    required: ["labelText"],
    optional: ["text", "cardBrandDetection"]
  }
}
```

## Integration Scenarios

### Required Scenario Guides:

1. **payment_form**
   - Complete payment form with card number, expiry, CVV
   - Form validation setup
   - Error handling implementation
   - Submit button integration

2. **card_input**
   - Single card number field
   - Brand detection setup
   - Real-time validation

3. **basic_form**
   - Simple form with GP input components
   - Basic validation
   - Form data collection

4. **custom_input_container**
   - Using GPDefaultInputContainer as base
   - Custom styling implementation
   - Custom behavior addition

## Error Handling Requirements

### Standard Error Response:

```javascript
{
  success: false,
  error: {
    code: "COMPONENT_NOT_FOUND",
    message: "Component 'GPInvalidComponent' not found",
    suggestions: ["GPCardNumberField", "GPCardExpiryDateField"]
  }
}
```

### Error Categories:
- `COMPONENT_NOT_FOUND` - Invalid component name
- `INVALID_PARAMETERS` - Wrong parameter types or values  
- `VALIDATION_FAILED` - Code validation errors
- `PARSE_ERROR` - Documentation parsing failures
- `GENERATION_ERROR` - Code generation failures

## Environment Configuration

### .env.example:

```bash
# Documentation path
DOCS_PATH=./docs

# Logging configuration
LOG_LEVEL=info

# Server configuration
MCP_SERVER_NAME=gp-coreui-assistant
MCP_SERVER_VERSION=1.0.0

# Development settings
NODE_ENV=development
```

## Testing Requirements

### Test Coverage Areas:

1. **Component Parser Tests**
   - Markdown parsing accuracy
   - Inheritance chain building
   - Search functionality
   - Error handling

2. **Code Generator Tests**
   - XML generation correctness
   - Java generation accuracy
   - Integration guide completeness
   - Template rendering

3. **Validator Tests**
   - XML validation rules
   - Java validation patterns
   - Error message formatting
   - Edge case handling

### Test Structure:

```javascript
// tests/component-parser.test.js
describe('ComponentParser', () => {
  //#region Test Setup
  beforeEach(() => {
    // Setup test environment
  });
  //#endregion

  //#region Component Loading Tests
  describe('loadComponents', () => {
    test('should load all component files', async () => {
      // Test implementation
    });
  });
  //#endregion

  //#region Search Tests
  describe('searchComponents', () => {
    test('should find components by name', () => {
      // Test implementation
    });
  });
  //#endregion
});
```

## Documentation Requirements

### README.md Structure:

```markdown
# GP Core UI MCP Server

## Installation
## Claude Desktop Configuration  
## Available Tools
## Usage Examples
## Development Setup
## Testing
## Troubleshooting
```

### Component Documentation Standards:

Each component doc should include:
- Overview and purpose
- Parameter table with inheritance
- Method descriptions
- Usage examples (XML and Java)
- Integration notes
- Common patterns

## Development Workflow

### Before Starting Work
1. Create branch using naming convention
2. Review MCP SDK documentation
3. Understand GP Core UI component structure
4. Set up development environment

### During Development
1. Follow #region code organization
2. Implement comprehensive error handling
3. Write tests for all functionality
4. Document all public APIs
5. Test with Claude Desktop integration

### Before Submitting
1. **CRITICAL**: All tests must pass
2. Update documentation
3. Test MCP server integration
4. Verify all 7 core tools work correctly
5. Validate error handling
6. Check code organization with #regions

## Quality Checklist

Before considering any task complete, verify:

- [ ] Branch name follows `YYMMMDD_Number_task_name` format
- [ ] **Code organized with meaningful #region tags**
- [ ] All 7 MCP tools implemented and working
- [ ] Component parser handles inheritance correctly
- [ ] Code generator produces valid XML/Java
- [ ] Validators catch common mistakes
- [ ] Comprehensive error handling implemented
- [ ] All tests pass with good coverage
- [ ] Documentation updated
- [ ] Claude Desktop integration tested
- [ ] Environment configuration working

## Resources

- **MCP SDK Documentation**: https://modelcontextprotocol.io/
- **Base Template**: https://github.com/ryaker/MCP-JS-Server-Template
- **GP Core UI Library**: Target library for component knowledge
- **Claude Desktop**: Primary integration target

## Support

For questions about MCP server architecture or implementation patterns, refer to the MCP SDK documentation and existing MCP server examples.