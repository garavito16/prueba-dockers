import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import './FormularioNote.css';
import imgDelete from './../../img/delete.png';

const FormularioNote = (props) => {

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [errores, setErrores] = useState([]);
    const [newCategorie, setNewCategorie] = useState("");
    const [listCategories, setListCategories] = useState([]);
    const [idNewCategorie, setIdNewCategorie] = useState(0);

    useEffect(() => {
        if (localStorage.getItem("id")) {
            if (props.idNote != 0) {
                axios.post("http://localhost:8080/api/note", { id: props.idNote })
                    .then(response => {
                        if (response.status === 200) {
                            setTitle(response.data.title);
                            setContent(response.data.content);
                            let aux = [];
                            for (let index = 0; index < response.data.listCategories.length; index++) {
                                aux.push({ 'Id': response.data.listCategories[index].id, 'name': response.data.listCategories[index].name });
                            }
                            setListCategories(aux);
                        }
                    })
                    .catch(err => {
                        console.log("ocurrio un error : " + err);
                    })
            }
        } else {
            props.history.push("/");
        }
    }, [])

    const enviarDatos = (e) => {
        e.preventDefault();
        let validar = true;
        let aux = [];

        if (title.length === 0) {
            validar = false;
            aux.push("El titulo de la nota es obligatorio");
        } else if (title.length < 3) {
            validar = false;
            aux.push("El titulo de la nota debe tener más de 2 caracteres");
        }

        if (content.length === 0) {
            validar = false;
            aux.push("El contenido de la nota es obligatorio");
        } else if (content.length < 3) {
            validar = false;
            aux.push("El contenido de la nota debe tener más de 2 caracteres");
        }

        if (validar) {
            if (props.idNote != 0) {
                axios.put("http://localhost:8080/api/note/update", { title, content, id: props.idNote, listCategories: listCategories })
                    .then(response => {
                        props.history.push("/notes");
                        props.setIdNote(0);
                    })
                    .catch(err => {
                        const mensajes = [];
                        for (let index = 0; index < err.response.data.cause.length; index++) {
                            mensajes.push(err.response.data.cause[index]);
                        }
                        setErrores(mensajes);
                    });
            } else {
                axios.post("http://localhost:8080/api/note/create", { title, content, listCategories: listCategories, idUser: localStorage.getItem('id') })
                    .then(response => {
                        props.history.push("/notes");
                    })
                    .catch(err => {
                        const mensajes = [];
                        for (let index = 0; index < err.response.data.cause.length; index++) {
                            mensajes.push(err.response.data.cause[index]);
                        }
                        setErrores(mensajes);
                    });
            }

        } else {
            setErrores(aux);
        }
    }

    const cancelar = () => {
        props.setIdNote(0);
        if (props.typeNote === '0') {
            props.history.push("/notes");
        } else {
            props.history.push("/notes/archived");
        }
    }

    const addCategorie = (e) => {
        e.preventDefault();
        setListCategories([...listCategories, { 'Id': idNewCategorie - 1, 'name': newCategorie }]);
        setIdNewCategorie(idNewCategorie - 1);
        setNewCategorie("");
    }

    const removeItem = (e, id) => {
        e.preventDefault();
        console.log(id);
        setListCategories(listCategories.filter(aux => aux.Id !== id));
    }

    return (
        <div className="containerThree">
            {
                errores.map((error, index) => {
                    return (
                        <div key={"index" + index}>
                            <p>{error}</p>
                        </div>
                    )
                })
            }
            <form className="formularioNote" onSubmit={(e) => enviarDatos(e)}>
                <h1 className="titleCreate">Create/Edit note</h1>
                <div className="grupoForm">
                    <label className="etiqueta">Title:</label>
                    <input className="ingresa" placeholder="Enter note title" type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
                </div>
                <div className="grupoForm">
                    <label className="etiqueta">Content:</label>
                    <textarea className="ingresa" placeholder="Enter content title" type="text" value={content} onChange={(e) => setContent(e.target.value)} ></textarea>
                </div>
                <div className="grupoForm">
                    <label className="etiqueta">Categories:</label>
                    <div className="divCategories">

                        {
                            listCategories.map((categorie, index) => {
                                return (
                                    <div className="divNewCategorie" key={"index" + index}>
                                        <span className="nameCategorie">{categorie.name}</span>
                                        <button className="btnDelete" onClick={(e) => removeItem(e, categorie.Id)}>
                                            <img className="imgDelete" src={imgDelete} />
                                        </button>
                                    </div>
                                )
                            })
                        }
                    </div>
                </div>
                <div className="grupoFormCategorie">
                    <input className="ingresa" placeholder="Enter new category" type="text" value={newCategorie} onChange={(e) => setNewCategorie(e.target.value)} />
                    <button className="btnAddCategorie" onClick={(e) => addCategorie(e)}>Add</button>
                </div>
                <div className="divBoton">
                    <button className="btnFormNote btnCancel" onClick={cancelar}>Cancel</button>
                    <input className="btnFormNote btnSave" type="submit" value="Save" />
                </div>
            </form>
        </div>
    )
}

export default FormularioNote;