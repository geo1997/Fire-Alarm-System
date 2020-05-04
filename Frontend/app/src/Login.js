import React, {Component} from 'react';
import {Link, Redirect,useHistory} from 'react-router-dom';
import Logo from "./images/navicon.png";
import axios from 'axios';

class Login extends Component {
    //initalize state 
    state = {
        email: '',
        password: '',
        redirect: false
    }

    //on change set the email state
    handleEmail(text){
        this.setState({email: text.target.value})
    }

    //on change set the password state
    handlePassword(text){
        this.setState({password: text.target.value})
    }


    //method to execute the login functinality
    login = e =>{

        e.preventDefault();
        console.log(this.state);
        //check if the fields are empty or not
        if(this.state.email==='' || this.state.password===''){

            alert('Please fill out email and password')

        }
        else {
            //using axios fetch the user details and validate the user
            axios.get("http://localhost:8080/getUser/"+this.state.email)
                .then(response =>{

                    if(response.data.password===this.state.password){
                        console.log(response)
                        this.props.history.push('/sensor')
                    }else{
                        alert('Invalid password or email');
                    }

                })
                .catch(error =>{
                    console.log(error)
                })
        }
    }




        //user interface for the login component
    render() {
        return (
            <div>
                <nav className="navbar navbar-light bg-dark mb-4">
                    <div className="navbar-brand text-light">
                        <img src={Logo} width="30" height="30" className="d-inline-block align-top mr-2" alt=""/>
                        Fire Alarm System
                    </div>
                </nav>
                <div className="col-sm-4 mt-3" style={{marginLeft:'30%'}}>
                    <div className="card" style={{marginTop:'40%'}}>
                        <div className="card-body">
                            <h3 className="card-title text-center mb-3">Login</h3>
                            <form className="login-form" onSubmit={this.login}>
                                <div className="form-group">
                                    <input type="email" className="form-control" id="exampleInputEmail1"
                                           aria-describedby="emailHelp" placeholder="Email" onChange={(text) =>{this.handleEmail(text)}}/>
                                </div>
                                <div className="form-group">
                                    <input type="password" className="form-control" id="exampleInputPassword1"
                                           placeholder="Password" onChange={(text) =>{this.handlePassword(text)}}/>
                                </div>
                                {/*<Link to={'/sensor'}><button type="submit" className="btn btn-primary btn-lg btn-block" onClick={() => this.login()}>Login</button></Link>*/}
                                <button type="submit" className="btn btn-primary btn-lg btn-block">Login</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;
