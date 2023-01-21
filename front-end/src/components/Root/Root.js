import userEvent from "@testing-library/user-event"
import React, { useEffect, useState } from "react"
import Profile from "./Profile"
import "./Root.css"

export default function Root(){

    return (
        <div className="home_container">
            <Profile/>
            <div className="home-item">
                <div className="item-description">
                <a className="title" href="cart">Cart</a>
                </div>
            </div>
            <div className="home-item">
                <div className="item-description">
                <a className="title" href="groups">Groups</a>
                </div>
            </div>
            <div className="home-item">
                <div className="item-description">
                <a className="title" href="inbox">Inbox</a>
                </div>
            </div>
            <div className="home-item">
                <div className="item-description">
                <a className="title" href="photoSession">Request Photo Session</a>
                </div>
            </div>
        </div>
    )
}