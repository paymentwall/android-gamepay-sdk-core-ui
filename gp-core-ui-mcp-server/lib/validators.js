class Validators {
    //#region XML Validation
    validateXMLUsage(componentName, code) {
        const valid = code.includes(`<${componentName}`);
        return { valid, errors: valid ? [] : ['missing component tag'] };
    }
    //#endregion

    //#region Java Validation
    validateJavaUsage(componentName, code) {
        const valid = code.includes(componentName);
        return { valid, errors: valid ? [] : ['missing class name'] };
    }
    //#endregion
}

module.exports = Validators;
