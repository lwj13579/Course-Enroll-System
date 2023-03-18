import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import {Button} from "@material-ui/core";

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

export default function CourseTable(props) {
    const classes = useStyles();

    return (
        <TableContainer component={Paper}>
            <Table className={classes.table} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>#</TableCell>
                        <TableCell align="right">Course Name</TableCell>
                        <TableCell align="right">Course Content</TableCell>
                        <TableCell align="right">Course Location</TableCell>
                        <TableCell align="right">Teacher ID</TableCell>
                        <TableCell align="right">Action</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.courses.map((row, index) => (
                        <TableRow key={row.name}>
                            <TableCell component="th" scope="row">
                                {index + 1}
                            </TableCell>
                            <TableCell align="right">{row.courseName}</TableCell>
                            <TableCell align="right">{row.courseContent}</TableCell>
                            <TableCell align="right">{row.courseLocation}</TableCell>
                            <TableCell align="right">{row.teacherId}</TableCell>
                            <TableCell align="right">
                                <Button variant="contained" color="primary"
                                        onClick={() => props.action(row.courseName)}>
                                    {props.label}
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
