const express = require('express');
const log = require('morgan');
const app = express();

app.use(require('./routes'));
app.use(log('dev'));

app.listen(3000, () => {
    console.log('Server On Port 3000');
})