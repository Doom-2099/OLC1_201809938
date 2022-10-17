const express = require('express');
const path = require('path');
const fs = require('fs');
const multer = require('multer');
const routes = express.Router();

const storage = multer.diskStorage({
   destination: path.resolve(__dirname, './Upload'),
   filename: function (req, file, cb) {
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
               res.json({ flag: false });
            } else {
               res.json({ flag: true });
            }
         });
      })
   })

   .post('/uploadFile/:id', upload.single('File'), (req, res) => {
      if (fs.existsSync(path.resolve(__dirname, './Upload/', req.params.id))) {
         fs.readFile(path.resolve(__dirname, './Upload/', req.params.id), 'utf-8', (err, data) => {
            if (err) {
               res.json({
                  flag: false,
                  message: 'Ocurrio Un Error Al Abrir El Archivo'
               });
            } else {
               res.json({
                  flag: true,
                  code: data,
                  name: req.params.id,
                  message: 'Archivo Abierto Correctamente'
               });
            }
         });
      } else {
         res.json({
            flag: false,
            message: 'El Archivo No Se Encontro'
         });
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

   .post('/downloadFile', (req, res) => {
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
               res.set({ 'filename': name });
               res.download(path.resolve(__dirname, './Upload/', name), name, (err) => {
                  if (err) {
                     res.json({
                        flag: false,
                        message: 'Ocurrio Un Error Al Descargar El Archivo'
                     });
                  } else {
                     res.json({
                        flag: true,
                        message: 'El Archivo Se Descargo Correctamente'
                     });
                  }
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
               message: 'Ocurrio Un Error Al Listar Los Archivos'
            });
         } else {
            res.json({
               flag: true,
               files: files,
               message: 'Se Listaron Los Archivos Correctamente'
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
                  message: 'Ocurrio Un Error Abriendo El Archivo'
               });
            } else {
               res.json({
                  flag: true,
                  message: 'El Archivo Se Abrio Correctamente'
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
      });
   });

module.exports = routes;