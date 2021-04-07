import React, { Component } from 'react'
import UserService from '../services/UserService';
import { Button, Container, Paper, Typography } from '@material-ui/core';

class AddUser extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            username: '',
            emailId: '',
            password: ''
        }
        this.changeUserNameHandler = this.changeUserNameHandler.bind(this);
        this.changeEmailHandler = this.changeEmailHandler.bind(this);
        this.changePasswordHandler = this.changePasswordHandler.bind(this);
        this.saveOrUpdateEmployee = this.saveOrUpdateEmployee.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            UserService.getUserById(this.state.id).then( (res) =>{
                let user = res.data;
                this.setState({username : user.username,
                    emailId : user.emailId,
                    password: user.password
                });
            });
        }        
    }
    saveOrUpdateEmployee = (e) => {
        e.preventDefault();
        let user = {username: this.state.username, email: this.state.email, password: this.state.password};
        console.log('user => ' + JSON.stringify(user));

        // step 5
        if(this.state.id === '_add'){
            UserService.addUser(user).then(res =>{
                this.props.history.push('/users');
            });
        }else{
            UserService.updateUser(user, this.state.id).then( res => {
                this.props.history.push('/users');
            });
        }
    }
    
    changeUserNameHandler= (event) => {
        this.setState({username: event.target.value});
    }
    changeEmailHandler= (event) => {
        this.setState({email: event.target.value});
    }
    changePasswordHandler= (event) => {
        this.setState({password: event.target.value});
    }

    cancel(){
        this.props.history.push('/users');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add User</h3>
        }else{
            return <h3 className="text-center">Update User</h3>
        }
    }
    render() { 
        return (
            <div>
                 
                <Container maxWidth="sm" style={{ marginTop: 20 }}>
                    <Paper elevation={5} style={{ padding: 8, justifyContent: "center", display: "flex" }} >
                    <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                        <form>

                            <Typography variant="h6" style={{ width: 'fit-content', margin: '' }}>User Name</Typography>
                            <input type="text" ref={this.state.username} onChange={this.changeUserNameHandler} placeholder="Enter UserName" name="firstName" required /><br></br><br></br>
                            <Typography variant="h6" style={{ width: 'fit-content', margin: '' }}>Email</Typography>
                            <input type="text" ref={this.state.email} onChange={this.changeEmailHandler} placeholder="Enter Email" name="lastName" required /><br></br><br></br>
                            <Typography variant="h6" style={{ width: 'fit-content', margin: '' }}>Password</Typography>
                            <input type="text" ref={this.state.password} onChange={this.changePasswordHandler} placeholder="Enter Password" name="address" required /><br></br><br></br>
                            <br></br><br></br>
                            <Button style={{ align: "center" }} variant="contained" onClick={this.saveOrUpdateEmployee} color="primary">Save</Button>
                            <Button style={{ align: "center" }} variant="contained"  onClick={this.cancel.bind(this)} color="primary">Cancel</Button>
                        </form>
                        </div>
                    </Paper>
                </Container>
            </div>
        )
    }
}

export default AddUser