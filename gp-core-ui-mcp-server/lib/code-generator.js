class CodeGenerator {
    //#region XML Generation
    generateXMLUsage(componentName, attributes = {}) {
        const attrs = Object.entries(attributes)
            .map(([k,v]) => `${k}="${v}"`).join(' ');
        return `<${componentName} ${attrs}/>`.trim();
    }
    //#endregion

    //#region Java Generation
    generateJavaUsage(componentName, variableName = 'component', includeListeners = false) {
        let code = `${componentName} ${variableName} = new ${componentName}(context);`;
        if (includeListeners) {
            code += `\n// add listeners here`;
        }
        return code;
    }
    //#endregion

    //#region Integration Guide
    getIntegrationGuide(scenario) {
        switch (scenario) {
            case 'payment_form':
                return ['Add card fields', 'Validate form'];
            case 'card_input':
                return ['Use GPCardNumberField'];
            default:
                return [];
        }
    }
    //#endregion
}

module.exports = CodeGenerator;
