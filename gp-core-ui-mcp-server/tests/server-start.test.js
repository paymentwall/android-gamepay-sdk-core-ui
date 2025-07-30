const test = require('node:test');
const assert = require('node:assert/strict');
const { server } = require('../server');

// Ensure server provides correct initialization info

test('initialize request returns server info', async () => {
  const response = await server.fallbackRequestHandler({ method: 'initialize', id: 1 });
  assert.equal(response.protocolVersion, '2024-11-05');
  assert.deepEqual(response.serverInfo, { name: 'gp-coreui-mcp-server', version: '0.1.0' });
});

test('tools/list returns available tools', async () => {
  const response = await server.fallbackRequestHandler({ method: 'tools/list', id: 2 });
  assert.ok(Array.isArray(response.tools));
  assert.ok(response.tools.find(t => t.name === 'get_component_list'));
});
