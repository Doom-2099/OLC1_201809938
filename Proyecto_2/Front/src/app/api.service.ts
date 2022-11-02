import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Error } from './Models/Error';
import { Symbol } from './Models/Symbol';

const headerDict = {
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
        return this.http.get
            <{ 
                flag: boolean,
                message: string,
                files: []
            }>
            ('http://localhost:3000/getFiles', requestOptions);
    }

    newFile(name: string) {
        return this.http.post
            <{
                flag: boolean,
                name: string
            }>('http://localhost:3000/newFile', { name: name }, requestOptions);
    }

    uploadFile(formData: FormData, name: string) {
        return this.http.post
            <{
                flag: boolean,
                message: string,
                code: string,
                name: string
            }>('http://localhost:3000/uploadFile/' + name, formData);
    }

    saveFile(filename: string, content: string) {
        return this.http.post
            <{
                flag: boolean,
                message: string        
            }>('http://localhost:3000/saveFile', { name: filename, content: content }, requestOptions);
    }

    nuevoTab(filename: string) {
        return this.http.post
            <{
                flag: boolean,
                message: string,
                code: string
            }>('http://localhost:3000/getFileForTab', { name: filename }, requestOptions);
    }

    runCode(code: string, filename: any) {
        return this.http.post
            <{
                flag: boolean,
                message: string,
                error: Error[],
                symbol: Symbol[]
            }>('http://localhost:3000/runCode', { code: code, filename: filename }, requestOptions);
    }

}
