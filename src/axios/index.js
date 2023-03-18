import axios from 'axios';
import cookies from "react-cookies";
import {TOKEN_COOKIE_NAME} from "../constant";

const token = cookies.load(TOKEN_COOKIE_NAME)
export default axios.create({
  baseURL: "http://course-enrollment-alb-506420894.us-east-2.elb.amazonaws.com:8080",
  headers: {
    "Authorization": `Bearer ${token}`
  }
})