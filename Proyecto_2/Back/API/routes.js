const express = require('express');
const path = require('path');
const fs = require('fs');
const multer = require('multer');
const parser = require('./Parser/grammar');
const graph = require('./Parser/Graph/AST');
const ListError = require('./Parser/Error/ListError');
const ListSymbol = require('./Parser/Symbols/ListSymbol');
const interprete = require('./Parser/Symbols/Interprete');
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
               }).status(200).end();
            } else {
               res.json({ 
                  flag: true,
                  name: name 
               }).status(200).end();
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
               }).status(200).end();
            } else {
               res.json({
                  flag: true,
                  code: data,
                  name: req.params.id,
                  message: 'Archivo Abierto Correctamente'
               }).status(200).end();
            }
         });
      } else {
         res.json({
            flag: false,
            message: 'El Archivo No Se Encontro'
         }).status(200).end();
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
               }).status(200).end();
            } else {
               res.json({
                  flag: true,
                  message: 'El Archivo Se Guardo Correctamente'
               }).status(200).end();
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
            }).status(200).end();
         } else {
            res.json({
               flag: true,
               files: files,
               message: 'Se Listaron Los Archivos Correctamente Desde El Back Version 2'
            }).status(200).end();
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
               }).status(200).end();
            } else {
               res.json({
                  flag: true,
                  message: 'El Archivo Se Abrio Correctamente',
                  code: data
               }).status(200).end();
            }
         });
      });
   })

   .post('/runCode', (req, res) => {
      req.on('data', data => {
         ListError.getInstance().clearLista();
         var code = JSON.parse(data)['code'];
         // EJECTUAR PARSER
         // RETORNAR ERRORES, SALIDAS, REPORTES
         var ast = parser.parse(code);

         if(ListError.getInstance().getLista().length > 0) {
            res.json({
               flag: false,
               message: 'El Codigo Contiene Errores',
               error: ListError.getInstance().getLista(),
               symbol: ListSymbol.getInstance().getLista()
            }).status(200).end();
         }

         var flag = graph.generarDOT(ast);

         interprete.analizarCode(ast);

         if(flag) {
            res.json({
               flag: true,
               message: 'El AST Se Genero Correctamente',
               error: ListError.getInstance().getLista(),
               symbol: ListSymbol.getInstance().getLista()
            }).status(200).end();
         } else {
            res.json({
               flag: false,
               message: 'El AST No Se Genero',
               error: ListError.getInstance().getLista(),
               symbol: ListSymbol.getInstance().getLista()
            }).status(200).end();
         }
      });
   });

module.exports = routes;