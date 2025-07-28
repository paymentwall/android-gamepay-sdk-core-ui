class CodeGenerator {
  constructor(parser) {
    this.parser = parser;
  }

  generateXMLUsage(componentName, attributes = {}) {
    const component = this.parser.getComponent(componentName);
    if (!component) throw new Error(`Component not found: ${componentName}`);
    let xml = component.xmlExample;
    if (!xml) {
      const attrs = Object.entries(attributes).map(([k,v]) => `    ${k}="${v}"`).join('\n');
      xml = `<${component.package}.${component.name}\n${attrs}\n/>`;
    } else if (Object.keys(attributes).length) {
      xml = xml.replace(/\/>$/, '');
      const attrs = Object.entries(attributes).map(([k,v]) => `    ${k}="${v}"`).join('\n');
      xml += `\n${attrs}\n/>`;
    }
    return xml;
  }

  generateJavaUsage(componentName, variableName = 'view', includeListeners = false) {
    const component = this.parser.getComponent(componentName);
    if (!component) throw new Error(`Component not found: ${componentName}`);
    let java = component.javaExample;
    if (!java) {
      java = `${componentName} ${variableName} = findViewById(R.id.${variableName});`;
    }
    if (includeListeners) {
      java += `\n// add listeners here`;
    }
    return java;
  }

  getIntegrationGuide(scenario) {
    switch (scenario) {
      case 'payment_form':
        return 'Use GPCardNumberField, GPCardExpiryDateField and GPCardCVVField inside a GPDefaultInputContainer. Validate before submission.';
      case 'card_input':
        return 'Place GPCardNumberField in your layout and read the card number using getCardNumber().';
      case 'basic_form':
        return 'Use GPDefaultInputContainer for simple text inputs. Set labelText and hintText as needed.';
      default:
        return 'No guide available.';
    }
  }
}

module.exports = CodeGenerator;
