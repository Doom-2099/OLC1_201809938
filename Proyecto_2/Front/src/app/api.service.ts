import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const headerDict = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Access-Control-Allow-Origin': '*'
}

const requestOptions = {
    headers: new HttpHeaders(headerDict)
}

@Injectable({
    providedIn: 'root'
})

export class ApiService {

    constructor(private http: HttpClient) { }

    // ---------> PETICIONES
    getFiles() {
        return this.http.get('http://localhost:3000/getFiles', requestOptions);
    }

    newFile() {
        return this.http.post('http://localhost:3000/newFile', { name: name }, requestOptions);
    }

    uploadFile() {
        return this.http.post('http://localhost:3000/uploadFile/', { name: name }, requestOptions);
    }

    saveFile() {
        return this.http.post('http://localhost:3000/saveFile', { name: name }, requestOptions);
    }

    downloadFile() {
        return this.http.post('http://localhost:3000/downloadFile', { name: name }, requestOptions);
    }

    nuevoTab() {
        return this.http.post('http://localhost:3000/getFileForTab', { name: name }, requestOptions);
    }

    runCode() {
        return this.http.post('http://localhost:3000/runCode', { name: name }, requestOptions);
    }

}
