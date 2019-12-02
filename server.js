const app = require('./app');

const nodeAppStarted = Date.now();
const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`Mock server started on port ${port} after ${Date.now() - nodeAppStarted} ms, running - stop with CTRL+C (or CMD+C)...`))
