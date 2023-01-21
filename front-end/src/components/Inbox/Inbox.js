import React, { useEffect, useState } from "react";
import UserInbox from "./UserInbox";
import PhotoInbox from "./PhotoInbox";
import './Inbox.css'
export default function Inbox() {

    if (localStorage.getItem('type')==='USER')
        return (<UserInbox/>)

    else if (localStorage.getItem('type')==='PHOTOGRAPHER')
        return (<PhotoInbox/>)
    else return (<p>Invalid User Type</p>)
}