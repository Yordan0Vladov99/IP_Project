import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function Children(){
    const [children, setChildren] = useState({})
    const {id} = useParams();
    useEffect(() => {
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
        }
    }; 

    fetch(`/api/groups/${id}/children`,requestOptions)
            .then((res => res.json()))
            .then(res => {
                setChildren(res)
            }).catch(error => {console.error(error)})
    }
    ,[])


        return(
            <ul>
               {
               Array.from(children).map(
                   group => <li key={group['id']}><a href={`/groups/${group['id']}`}>{group['name']}</a></li>
               )
               }
           </ul>
           )
}
