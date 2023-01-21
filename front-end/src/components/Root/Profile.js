import React from "react"

export default function Profile(){
    const Logout = () => {
        localStorage.clear()
        window.location.reload()
    }
    if (localStorage.getItem('token')){
        return (
        <div className="home-item">
            <div className="item-description">
            <button className="title" onClick={Logout}>Logout</button>
            </div>
        </div>
        )
    }
    else {
        return (
            <div>
                 <div className="home_item">
            <div className="item-description">
                <a className="title" href="login">Login</a>
            </div>
        </div>
            <div className="home-item">
                <div className="item-description">
                <a className="title" href="registration">Register</a>
            </div>
            </div>
            </div>
        )
    }
}