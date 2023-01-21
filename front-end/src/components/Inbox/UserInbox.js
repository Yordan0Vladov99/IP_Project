import React, { useEffect, useState } from "react";

export default function UserInbox(){
    const [itemRequests, setItemRequests] = useState({})
    const [sessionRequests, setSessionRequests] = useState({})

    useEffect(() => {
        const itemRequestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
            }
        };

        fetch(`/api/users/${localStorage.getItem('email')}/sentItemRequests`,itemRequestOptions)
            .then((res) => res.json())
            .then((res) => {
                setItemRequests(res)
            }).catch(error => {console.error(error)})

            const sessionRequestOptions = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization' : `Bearer ${localStorage.getItem('token')}`
                }
            };

            fetch(`/api/users/${localStorage.getItem('email')}/sentSessionRequests`,itemRequestOptions)
            .then((res) => res.json())
            .then((res) => {
                setSessionRequests(res)
            }).catch(error => {console.error(error)})
        
        
    },[])

    return(
        <div>
        <h1>Items</h1>
        <div>
            {Array.from(itemRequests).map(request => 
                        <div className="request">
                            <div>
                                <div className="tags">
                                    <span className="author">Status:{request['status']}</span>
                                    <span className="author">On: {request['created']}</span>
                                </div>
                                <div className="description">
                                    <p>{request['description']}</p>
                                </div>  
                            </div>
                            <img src= {`/api/uploads/${request['img']}`} />
                        </div>
            )}
        </div>
        <h1>Sessions</h1>
        <div>
            {Array.from(sessionRequests).map( request =>
                    <div id={request['id']} className="request">
                    <div>
                        <div>
                            <span className="author">Date: {request['date']} </span>
                        </div>
                        <div>
                            <span className="author">At: {request['location']} </span>
                        </div>
                        <div>
                            <span className="author">People: {request['people']}</span>
                        </div>
                        <div>
                            <span className="author">Status: {request['status']}</span>
                        </div>
                        <div className="description">
                            <p>{request['description']}</p>
                        </div>
                    </div>
                </div>

            )}
        </div>
        </div>
    )
}