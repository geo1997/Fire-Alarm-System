import React, {Component} from 'react';
import {Link} from 'react-router-dom';

class Login extends Component {
    state = {
        email: '',
        password: '',
    }

    handleEmail(text){
        this.setState({email: text.target.value})
    }

    handlePassword(text){
        this.setState({email: text.target.value})
    }

    login(){
        let obj = {}
        obj.email = this.state.email;
        obj.password = this.state.password;

        if(this.state.username==='' && this.state.password===''){

            this.setState({showSuccessMessage:true})
            this.setState({hasLoginFailed:false})
        }
        else {
            this.setState({showSuccessMessage:false})
            this.setState({hasLoginFailed:true})
        }
    }

    render() {
        return (
            <div className="col-sm-4 mt-3" style={{marginLeft:'30%'}}>
                <div className="card" style={{marginTop:'40%'}}>
                    <div className="card-body">
                        <h3 className="card-title text-center mb-3">Login</h3>
                        <form className="login-form">
                            <div className="form-group">
                                <input type="email" className="form-control" id="exampleInputEmail1"
                                       aria-describedby="emailHelp" placeholder="Email" onChange={(text) =>{this.handleEmail(text)}}/>
                            </div>
                            <div className="form-group">
                                <input type="password" className="form-control" id="exampleInputPassword1"
                                       placeholder="Password" onChange={(text) =>{this.handlePassword(text)}}/>
                            </div>
                            <Link to={'/sensor'}><button type="submit" className="btn btn-primary btn-lg btn-block" onClick={() => this.login()}>Login</button></Link>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;
