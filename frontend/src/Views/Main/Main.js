
import React, { useEffect, useState } from 'react';
import { Switch, Link, Route, withRouter } from 'react-router-dom';
import './Main.css';
import FormularioUsuario from '../../Components/FormularioUsuario/FormularioUsuario';
import ListaNotes from '../../Components/ListaNotes/ListaNotes';
import FormularioNote from '../../Components/FormularioNote/FormularioNote';

function Main(props) {

    const [idNote, setIdNote] = useState(0);
    const [typeNote, setTypeNote] = useState('0');

    const logout = () => {
        localStorage.clear();
        props.history.push("/");
    }

    return (

        <Switch>

            <Route exact path="/" render={(routeProps) =>
                <FormularioUsuario
                    {...routeProps}
                />}
            />

            <Route exact path="/notes" render={(routeProps) =>
                <>
                    <div className='portada'>
                        <div className='divBtnLogout'>
                            <button className='btnLogout' onClick={logout}>Logout</button>
                        </div>
                        <h1 className='titleWelcome'>Welcome, {localStorage.getItem('usuario')}</h1>
                    </div>
                    <ListaNotes
                        setTypeNote={setTypeNote}
                        typeNote={"0"}
                        setIdNote={setIdNote}
                        default={"0"}
                        {...routeProps}
                    />
                </>
            }
            />

            <Route exact path="/notes/archived" render={(routeProps) =>
                <>
                    <div className='portada'>
                        <div className='divBtnLogout'>
                            <button className='btnLogout' onClick={logout}>Logout</button>
                        </div>
                        <h1 className='titleWelcome'>Welcome, {localStorage.getItem('usuario')}</h1>
                    </div>
                    <ListaNotes
                        setTypeNote={setTypeNote}
                        typeNote={"1"}
                        setIdNote={setIdNote}
                        default={"0"}
                        {...routeProps}
                    />
                </>
            }
            />

            <Route exact path="/notes/new" render={(routeProps) =>
                <>
                    <div className='portada'>
                        <div className='divBtnLogout'>
                            <button className='btnLogout' onClick={logout}>Logout</button>
                        </div>
                        <h1 className='titleWelcome'>Welcome, {localStorage.getItem('usuario')}</h1>
                    </div>
                    <FormularioNote
                        idNote={idNote}
                        setTypeNote={setTypeNote}
                        typeNote={typeNote}
                        setIdNote={setIdNote}
                        {...routeProps}
                    />
                </>
            }
            />

            <Route exact path="/notes/edit" render={(routeProps) =>
                <>
                    <div className='portada'>
                        <div className='divBtnLogout'>
                            <button className='btnLogout' onClick={logout}>Logout</button>
                        </div>
                        <h1 className='titleWelcome'>Welcome, {localStorage.getItem('usuario')}</h1>
                    </div>
                    <FormularioNote
                        idNote={idNote}
                        setTypeNote={setTypeNote}
                        typeNote={typeNote}
                        setIdNote={setIdNote}
                        {...routeProps}
                    />
                </>
            }
            />
        </Switch>

    )
}

export default withRouter(Main);