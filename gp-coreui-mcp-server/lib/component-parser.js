const fs = require('fs');
const path = require('path');

class ComponentParser {
  constructor(docsPath) {
    this.docsPath = docsPath;
    this.components = new Map();
    this.loadComponents();
  }

  loadComponents() {
    const compDir = path.join(this.docsPath, 'components');
    if (!fs.existsSync(compDir)) return;
    const files = fs.readdirSync(compDir);
    files.forEach(file => {
      if (!file.endsWith('.md')) return;
      const content = fs.readFileSync(path.join(compDir, file), 'utf8');
      const nameMatch = content.match(/^#\s*(\w+)/m);
      if (!nameMatch) return;
      const name = nameMatch[1];
      const xmlMatch = content.match(/## 3\. How to use in XML\n```xml\n([\s\S]*?)```/m);
      const javaMatch = content.match(/## 4\. How to use in Activity[^\n]*\n```java\n([\s\S]*?)```/m);
      const paramSectionMatch = content.match(/## 2\. Params definition\n([\s\S]*?)(\n##|$)/m);
      const parameters = {};
      if (paramSectionMatch) {
        const lines = paramSectionMatch[1].split('\n');
        lines.forEach(l => {
          const bullet = l.match(/-\s*\*?(\w+)\*?\s*[-–:]\s*(.+)/);
          if (bullet) {
            parameters[bullet[1]] = { description: bullet[2].trim() };
          }
        });
      }
      const descMatch = content.match(/## 1\. Introduction\n([\s\S]*?)(\n##|$)/m);
      const description = descMatch ? descMatch[1].replace(/\n/g, ' ').trim() : '';
      const component = {
        name,
        package: 'com.terminal3.gpcoreui.components',
        description,
        parameters,
        xmlExample: xmlMatch ? xmlMatch[1].trim() : '',
        javaExample: javaMatch ? javaMatch[1].trim() : '',
        category: name.includes('Card') ? 'card' : name.includes('Dropdown') ? 'dropdown' : 'input'
      };
      this.components.set(name, component);
    });
  }

  getComponent(name) {
    return this.components.get(name);
  }

  getAllComponents() {
    return Array.from(this.components.values());
  }

  searchComponents(query, category) {
    query = query.toLowerCase();
    return this.getAllComponents().filter(c => {
      const matchQuery = c.name.toLowerCase().includes(query) || c.description.toLowerCase().includes(query);
      const matchCat = category ? c.category === category : true;
      return matchQuery && matchCat;
    });
  }
}

module.exports = ComponentParser;
