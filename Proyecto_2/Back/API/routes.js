const express = require('express');
const path = require('path');
const fs = require('fs');
const multer = require('multer');
const parser = require('./Parser/grammar');
const text = require('./aux');
const routes = express.Router();

const storage = multer.diskStorage({
   destination: function(res, file, cb) {
      cb("", path.resolve(__dirname, './Upload'));
   },

   filename: function (res, file, cb) {
      cb("", file.originalname);
   }
});

const upload = multer({
   storage: storage
});

routes
   .get('/', (req, res) => {

   })

   .post('/newFile', (req, res) => {
      req.on('data', (data) => {
         var name = JSON.parse(data)['name'];
         fs.writeFile(path.resolve(__dirname, './Upload/' + name + '.txt'), '', (err) => {
            if (err) {
               res.json({ 
                  flag: false,
                  name: name
               }).status(200);
            } else {
               res.json({ 
                  flag: true,
                  name: name 
               }).status(200);
            }
         });
      })
   })

   .post('/uploadFile/:id', upload.single('file'), (req, res) => {
      if (fs.existsSync(path.resolve(__dirname, './Upload/', req.params.id))) {
         fs.readFile(path.resolve(__dirname, './Upload/', req.params.id), 'utf-8', (err, data) => {
            if (err) {
               res.json({
                  flag: false,
                  message: 'Ocurrio Un Error Al Abrir El Archivo',
                  code: '',
                  name: '',
               }).status(200);
            } else {
               res.json({
                  flag: true,
                  code: data,
                  name: req.params.id,
                  message: 'Archivo Abierto Correctamente'
               }).status(200);
            }
         });
      } else {
         res.json({
            flag: false,
            message: 'El Archivo No Se Encontro'
         }).status(200);
      }
   })

   .post('/saveFile', (req, res) => {
      req.on('data', (data) => {
         var content = JSON.parse(data)['content'];
         var name = JSON.parse(data)['name'];

         fs.writeFile(path.resolve(__dirname, './Upload/', name), content, (err) => {
            if (err) {
               res.json({
                  flag: false,
                  message: 'El Archivo No Se Guardo Correctamente'
               });
            } else {
               res.json({
                  flag: true,
                  message: 'El Archivo Se Guardo Correctamente'
               });
            }
         });
      });
   })

   .get('/getFiles', (req, res) => {
      fs.readdir(path.resolve(__dirname, './Upload/'), (err, files) => {
         if(err) {
            res.json({
               flag: false,
               message: 'Ocurrio Un Error Al Listar Los Archivos',
               files: []
            });
         } else {
            res.json({
               flag: true,
               files: files,
               message: 'Se Listaron Los Archivos Correctamente Desde El Back Version 2'
            });
         }
      });
   })

   .post('/getFileForTab', (req, res) => {
      req.on('data', (data) => {
         var name = JSON.parse(data)['name'];
         fs.readFile(path.resolve(__dirname, './Upload/', name), 'utf-8', (err, data) => {
            if(err) {
               res.json({
                  flag: false,
                  message: 'Ocurrio Un Error Abriendo El Archivo',
                  code: ''
               });
            } else {
               res.json({
                  flag: true,
                  message: 'El Archivo Se Abrio Correctamente',
                  code: data
               });
            }
         });
      });
   })

   .post('/runCode', (req, res) => {
      req.on('data', data => {
         var code = JSON.parse(data)['code'];
         // EJECTUAR PARSER
         // RETORNAR ERRORES, SALIDAS, REPORTES
         parser.parse(code);
      });
   });

module.exports = routes;