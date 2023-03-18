import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import { Link } from 'react-router-dom';
import LoginDialog from './LoginDialog';
import cookies from "react-cookies";
import {TOKEN_COOKIE_NAME} from "../constant";

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
}));

export default function Navbar() {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const token = cookies.load(TOKEN_COOKIE_NAME);   //load cookie to memory
  const prompt = token ? "Logout" : "Login";  //authorized --> show login , else show log out 

  const handleClickOpen = () => {
    if (token) {
      //logout
      cookies.remove(TOKEN_COOKIE_NAME);
      window.location.reload();
    } else {
      setOpen(true) 
      //login
    }
  };

  const handleClose = () => {
    setOpen(false);
  };
  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" className={classes.title}>
            Course Enrollment Service
          </Typography>
          <Button color="inherit" component={Link} to="/" >All Courses</Button>
          <Button color="inherit" component={Link} to="/enrolled">Enrolled Courses</Button>
          <Button color="inherit" onClick= {handleClickOpen}>{prompt}</Button>
          
        </Toolbar>
      </AppBar>
      <LoginDialog open={open} handleClickOpen={handleClickOpen} handleClose={handleClose}/>
    </div>
  );
}
