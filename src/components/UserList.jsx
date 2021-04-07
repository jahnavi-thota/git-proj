import React, { Component } from 'react'
import UserService from '../services/UserService'
import { Button, ButtonGroup, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@material-ui/core';


class UserList extends Component {
    constructor(props) {
        super(props)

        this.state = {
                users: []
        }
        this.addUser = this.addUser.bind(this);
        this.editUser = this.editUser.bind(this);
        this.deleteUser = this.deleteUser.bind(this);
    }

    deleteUser(id){
        UserService.deleteUser(id).then( res => {
            this.setState({users: this.state.users.filter(user => user.id !== id)});
        });
    }
    editUser(id){
        this.props.history.push(`/add-user/${id}`);
    }

    componentDidMount(){
        UserService.viewUsers().then((res) => {
            this.setState({ users: res.data});
    });
    }

    addUser(){
        this.props.history.push('/add-user/_add');
    }

    render() {
        return (

            <div>
                 <h2 className="text-center">Users List</h2>
                 <div className = "row">
                 <ButtonGroup variant="contained" color="primary" aria-label="contained primary button group" > 
                   <Button onClick={this.addUser}>Add User</Button>
                 </ButtonGroup>
                </div>
                <br></br><br></br>
                <TableContainer component={Paper}>
                    <Table aria-label="customized table">
                        <TableHead >
                            <TableRow style={{color:"#3f51b5",  fontSize: 'medium'}}>
                                <TableCell align="center" style={{color:"#3f51b5",  fontSize: 'medium'}} >#</TableCell>
                                <TableCell align="center" style={{color:"#3f51b5",  fontSize: 'medium'}}>User Name</TableCell>
                                <TableCell align="center" style={{color:"#3f51b5",  fontSize: 'medium'}}>Email Id</TableCell>
                               <TableCell align="center" style={{color:"#3f51b5",  fontSize: 'medium'}}>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        {this.state && this.state.users &&<TableBody>
                            {this.state.users.map((user, i) => (
                                <TableRow key={i}>
                                    <TableCell component="th" scope="row">
                                        {i + 1}
                                    </TableCell>
                                    <TableCell align="center">{user.username}</TableCell>
                                    <TableCell align="center">{user.email}</TableCell>
                                    <TableCell align="center">
                                        <ButtonGroup variant="contained" color="primary" aria-label="contained primary button group" > 
                                            <Button onClick={() => this.editUser(user.id)}>Edit</Button>
                                            <Button onClick={() => this.deleteUser(user.id)}>Delete</Button>
                                        </ButtonGroup>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>}
                    </Table>
                </TableContainer>
            </div>
        )
    }
}


export default UserList