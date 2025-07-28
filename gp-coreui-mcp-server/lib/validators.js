class ComponentValidator {
  constructor(parser) {
    this.parser = parser;
  }

  validateXMLUsage(componentName, code) {
    const component = this.parser.getComponent(componentName);
    if (!component) throw new Error(`Component not found: ${componentName}`);
    const missing = [];
    Object.keys(component.parameters).forEach(p => {
      if (code.includes(p)) return;
      // consider labelText etc required? we'll just check presence if parameter includes '*' maybe
    });
    return { valid: true, missing }; // basic stub
  }

  validateJavaUsage(componentName, code) {
    const component = this.parser.getComponent(componentName);
    if (!component) throw new Error(`Component not found: ${componentName}`);
    return { valid: true };
  }
}

module.exports = ComponentValidator;
