const express = require('express');
const log = require('morgan');
const cors = require('cors');
const app = express();

const config = {
    application: {
        cors: {
            server: [
                {
                    origin: "localhost:4200",
                    credentials: true
                }
            ]
        }
    }
}

app.use(express.static('./Img'));
app.use(express.static('./JSON'));
app.use(require('./routes'));
app.use(log('dev'));
app.use(cors(config.application.cors.server));

app.listen(3000, () => {
    console.log('Server On Port 3000');
})