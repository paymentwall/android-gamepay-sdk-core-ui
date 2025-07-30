const fs = require('fs');
const path = require('path');

class ComponentParser {
    //#region Fields and Constants
    constructor(docsPath) {
        this.docsPath = docsPath;
        this.components = new Map();
        this.categories = new Set();
    }
    //#endregion

    //#region Component Loading
    loadComponents() {
        const compsDir = path.join(this.docsPath, 'components');
        if (!fs.existsSync(compsDir)) return;
        const files = fs.readdirSync(compsDir);
        files.forEach(file => {
            if (!file.endsWith('.md')) return;
            const data = fs.readFileSync(path.join(compsDir, file), 'utf8');
            const nameMatch = data.match(/^#\s*(\w+)/m);
            const descMatch = data.match(/\n\n([^\n]+)/);
            const name = nameMatch ? nameMatch[1] : path.basename(file, '.md');
            const description = descMatch ? descMatch[1] : '';
            const component = { name, description, file };
            this.components.set(name, component);
        });
    }
    //#endregion

    //#region Getters
    getComponent(name) {
        return this.components.get(name);
    }

    getAllComponents() {
        return Array.from(this.components.values());
    }

    searchComponents(query, category) {
        const q = query.toLowerCase();
        return this.getAllComponents().filter(c => c.name.toLowerCase().includes(q));
    }
    //#endregion
}

module.exports = ComponentParser;
