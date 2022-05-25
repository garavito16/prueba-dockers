
import axios from "axios";
import { useEffect, useState } from "react";
import './ListaNotes.css';
import imgEdit from './../../img/edit.png';
import imgRemove from './../../img/remove.png';
import imgArchived from './../../img/archived.png';
import imgUnarchived from './../../img/unarchived.png';

import imgCreate from './../../img/create.png';
import imgViewArchived from './../../img/viewArchived.png';
import imgViewUnarchived from './../../img/viewUnarchived.png';

export default props => {
    const [notes, setNotes] = useState([]);
    const [recargaNotes, setRecargaNotes] = useState(true);
    const [listCategories, setListCategories] = useState([]);
    const [notesFiltrados, setNotesFiltrados] = useState([]);

    useEffect(() => {
        if (recargaNotes) {
            axios.post('http://localhost:8080/api/note/user', { id: localStorage.getItem('id'), archived: props.typeNote })
                .then(response => {
                    if (response.status === 200) {
                        console.log(response.data);
                        setNotes(response.data);
                        setNotesFiltrados(response.data);
                        setRecargaNotes(false);

                        // filtrar categories
                        let auxLista = [];
                        for (let i = 0; i < response.data.length; i++) {
                            if (response.data[i].archived === props.typeNote) {
                                let aux = response.data[i].listCategories;
                                for (let j = 0; j < aux.length; j++) {
                                    auxLista.push({ 'id': aux[j].id, 'name': aux[j].name });
                                }
                            }
                        }
                        setListCategories(auxLista);
                    }

                })
                .catch(err => {
                    const mensajes = [];
                    for (let index = 0; index < err.response.data.cause.length; index++) {
                        mensajes.push(err.response.data.cause[index]);
                    }
                    props.history.push("/");
                })
        }
    }, [recargaNotes]);

    const filterCategories = (id_categorie) => {
        if (id_categorie !== "0") {
            let auxLista = [];
            for (let i = 0; i < notes.length; i++) {
                let aux = notes[i].listCategories;
                for (let j = 0; j < aux.length; j++) {
                    if (aux[j].id == id_categorie) {
                        auxLista.push(notes[i]);
                        break;
                    }
                }
            }
            setNotesFiltrados(auxLista);
        } else {
            setNotesFiltrados(notes);
        }
    }

    const changeArchivedNote = (id) => {
        let aux = (props.typeNote === '0') ? "1" : "0";
        axios.put('http://localhost:8080/api/note/updateArchiveStatus', { id, archived: aux })
            .then(response => {
                if (response.status === 202) {
                    console.log(response);
                    setRecargaNotes(true);
                }
            })
            .catch(err => {
                const mensajes = [];
                for (let index = 0; index < err.response.data.cause.length; index++) {
                    mensajes.push(err.response.data.cause[index]);
                }
                props.history.push("/");
            });
    }

    const deleteNote = (id_note) => {
        console.log("id de note " + id_note);
        var result = window.confirm("Are you sure you want to delete this note");
        if (result) {
            axios.delete('http://localhost:8080/api/note/delete', { data: { id: id_note } })
                .then(response => {
                    setRecargaNotes(true);
                })
                .catch(err => {
                    console.log("el error es :" + err);
                });
        }
    }

    const viewCreateNote = (e) => {
        e.preventDefault();
        props.history.push("/notes/new");
    }

    const editNote = (id) => {
        props.setIdNote(id);
        props.history.push("/notes/edit");
    }

    const changeTypeNote = (type) => {
        props.setTypeNote(type);
        if (type === '0') {
            props.history.push("/notes");
        } else {
            props.history.push("/notes/archived");
        }
        setRecargaNotes(true);
    }

    return (
        <div className="containerTwo">
            {
                (props.typeNote === '0') ?
                    <div className="divFragment">
                        <h2 className="titleMyNotes">My notes</h2>
                        <button className="btnList" onClick={(e) => viewCreateNote(e)}>
                            <img src={imgCreate} />
                            <span>Create note</span>
                        </button>
                        <button className="btnList" onClick={(e) => changeTypeNote('1')}>
                            <img src={imgViewArchived} />
                            <span>Archived notes</span>
                        </button>
                    </div>
                    :
                    <div className="divFragment">
                        <h2 className="titleMyNotes">Archived Notes</h2>
                        <button className="btnList" onClick={(e) => changeTypeNote('0')}>
                            <img src={imgViewUnarchived} />
                            <span>Unarchived notes</span>
                        </button>
                    </div>
            }

            <div className="divSelect">
                <select className="selectCategorie" defaultValue={props.default} id="seleccionar" onChange={(e) => filterCategories(e.target.value)}>
                    <option value={"0"}>--- Select categorie ---</option>
                    {
                        listCategories.map((categorie, index) => {
                            return (
                                <option value={categorie.id} key={"index" + index}>
                                    {categorie.name}
                                </option>
                            )
                        })
                    }
                </select>
            </div>

            <div className="divNotes">
                {
                    notesFiltrados.map((note, index) => {
                        return (
                            <div className="cuadrito" key={"Indice" + index}>
                                <div className="titleNote">
                                    {note.title}
                                </div>
                                <div className="dateNote">
                                    <span>Last Edited: {(note.updatedAt === null) ? note.createdAt.slice(0,10) : note.updatedAt.slice(0,10)}</span>
                                </div>
                                <div className="divBotones">
                                    <button className="btnNote btnArchived" onClick={(e) => changeArchivedNote(note.id)}>
                                        {(props.typeNote === '0') ? 
                                            <img src={imgArchived} />
                                            : 
                                            <img src={imgUnarchived} />
                                        }
                                    </button>
                                    <button className="btnNote btnEdit" onClick={(e) => editNote(note.id)}>
                                        <img src={imgEdit} />
                                    </button>
                                    <button className="btnNote btnRemove" onClick={(e) => deleteNote(note.id)}>
                                        <img src={imgRemove} />
                                    </button>
                                </div>
                            </div>
                        )
                    })
                }
            </div>
        </div>
    )
}
