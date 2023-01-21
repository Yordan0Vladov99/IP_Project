import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import Root from './components/Root/Root';
import Login from './components/Login/Login';
import Register from './components/Register/Register';
import Group from './components/Group/Group';
import {
  createBrowserRouter,
  RouterProvider,
  Navigate
} from "react-router-dom"
import GroupHomePage from './components/GroupHomePage/GroupHomePage';
import Cart from './components/Cart/Cart';
import Inbox from './components/Inbox/Inbox'
import PhotoSessionForm from './components/PhotoSessionForm/PhotoSessionForm';

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root/>,
  },
  {
    path: "login",
    element: localStorage.getItem("email") ? <Navigate replace to='/'/> : <Login/>
  },
  {
    path: "registration",
    element: localStorage.getItem("email") ? <Navigate replace to='/'/> : <Register/>,
  },
  {
    path: "groups",
    element: <GroupHomePage/>
  },
  {
    path: "cart",
    element: <Cart/>
  },
  {
    path: "groups/:id",
    element: <Group/>
  },
  {
    path: "inbox",
    element: <Inbox/>
  },
  {
    path: "photoSession",
    element: <PhotoSessionForm/>
  }
  /*
  {
    path: "cart",
    element: <Cart/>,
  },
  {
    path: "inbox",
    element: <Inbox/>
  }
  */
])

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
