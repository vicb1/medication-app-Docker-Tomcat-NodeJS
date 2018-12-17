// This file configures a web server for testing the production build
// on your local machine.

import express from 'express';
import {chalkProcessing} from './chalkConfig';
import path from 'path';

/* eslint-disable no-console */

console.log(chalkProcessing('Opening production build...'));

const PORT = 4000;
const HOST = '0.0.0.0';

const app = express();
app.use(express.static('dist'));
app.get('*', (req,res) =>{
  res.sendFile(path.join(__dirname, '../dist', 'index.html'));
});

app.listen(PORT, HOST);
console.log(chalkProcessing(`Running on http://${HOST}:${PORT}`));