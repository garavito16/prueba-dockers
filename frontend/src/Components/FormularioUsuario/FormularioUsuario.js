import axios from "axios";
import { useState } from "react";
import './FormularioUsuario.css';

export default props => {

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [erroresRegistro, setErroresRegistro] = useState([]);

    const [emailLogin, setEmailLogin] = useState("");
    const [passwordLogin, setPasswordLogin] = useState("");
    const [erroresLogin, setErroresLogin] = useState([]);

    const registrarUsuario = (e) => {
        e.preventDefault();
        axios.post("http://localhost:8080/api/user/register", { name, email, password, confirmPassword })
            .then(response => {
                if (response.status === 201) {
                    localStorage.setItem('id', response.data.id);
                    localStorage.setItem('usuario', response.data.name);
                    props.history.push('/notes');
                }
            })
            .catch(err => {
                const mensajes = [];
                for (let index = 0; index < err.response.data.cause.length; index++) {
                    mensajes.push(err.response.data.cause[index]);
                }
                setErroresRegistro(mensajes);
            })
    }

    const loguear = (e) => {
        e.preventDefault();
        axios.post("http://localhost:8080/api/user/login", { email: emailLogin, password: passwordLogin })
            .then(response => {
                if (response.status === 202) {
                    localStorage.setItem('id', response.data.id);
                    localStorage.setItem('usuario', response.data.name);
                    props.history.push('/notes');
                }
            })
            .catch(err => {
                const mensajes = [];
                for (let index = 0; index < err.response.data.cause.length; index++) {
                    mensajes.push(err.response.data.cause[index]);
                }
                setErroresLogin(mensajes);
            })
    }

    return (
        <div className="containerOne">
            <div className="containerRegister">
                <form className="formulario" onSubmit={(e) => registrarUsuario(e)}>
                    <div className="errores">
                        {
                            erroresRegistro.map((err, index) => {
                                return (
                                    <p key={index}>{err}</p>
                                )
                            })
                        }
                    </div>
                    <h2 className="title">Register</h2>
                    <div className="formGroup">
                        <label>
                            Name:
                        </label>
                        <input type="text" id="name" name="name" value={name} onChange={(e) => setName(e.target.value)} />
                    </div>
                    <div className="formGroup">
                        <label>
                            Email:
                        </label>
                        <input type="text" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <div className="formGroup">
                        <label>
                            Password:
                        </label>
                        <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <div className="formGroup">
                        <label>
                            Confirm Password:
                        </label>
                        <input type="password" id="confirmPassword" name="confirmPassword" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
                    </div>
                    <button className="btn btnRegister" type="submit">
                        Register
                    </button>
                </form>
            </div>
            <div className="containerLogin">
                <form className="formulario" onSubmit={(e) => loguear(e)}>
                    <div className="errores">
                        {
                            erroresLogin.map((err, index) => {
                                return (
                                    <p key={index}>{err}</p>
                                )
                            })
                        }
                    </div>
                    <h2 className="title">Login</h2>
                    <div className="formGroup">
                        <label>
                            Email:
                        </label>
                        <input type="text" id="email" name="email" value={emailLogin} onChange={(e) => setEmailLogin(e.target.value)} />
                    </div>
                    <div className="formGroup">
                        <label>
                            Password:
                        </label>
                        <input type="password" id="password" name="password" value={passwordLogin} onChange={(e) => setPasswordLogin(e.target.value)} />
                    </div>
                    <button className="btn btnLogin" type="submit">
                        Login
                    </button>
                </form>
            </div>
        </div>

    )
}