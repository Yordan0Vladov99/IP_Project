import React from "react";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";


function downloadImages(){
    var user = JSON.parse(localStorage.getItem('email'));
                if(user===null){
                    window.location.href = 'login';
                    return;
                }
                const indexes = Array.from(this.photos)
                for (let i = 0; i < indexes.length; ++i) {
                    const index = indexes[i];
    
                    if (document.getElementById(`${index}-button`).checked) {
                        var link = document.createElement("a");
                        link.download = `${index}-pic`;
                        link.href = document.getElementById(`${index}-pic`).src;
                        link.click();
                    }
                }
}


function uploadFile(id){
    let user = localStorage.getItem('email')
    if(!user){
        window.location.href = 'login';
        return;
    }

    const files = document.getElementById("file").files;
    
    if (files.length > 0) {

        var formData = new FormData();
        formData.append("file", files[0]);
        formData.append("user", localStorage.getItem('email'))
        formData.append("group",id)
        const requestOptions = {
            method: "POST",
            body: formData,
            headers: {
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
        }
        }

        fetch("/api/photos/upload", requestOptions)
        .then(res => res.json())
        .then(res => {
            console.log(res)
        }).catch(error => console.error(error))
        
        window.location.reload()

    } else {
        alert("Please select a file");
    }
}

function getAlbumStr(){
    let str=""
    const indexes = Array.from(this.state.photos)
    document.getElementById('')
    for (let i = 0; i < indexes.length; ++i) {
        const index = indexes[i];

        if (document.getElementById(`${index}-button`).checked) {
            const image = document.getElementById(`${index}-pic`).src;
            const img_name = image.replace(/^.*[\\\/]/, ''); 
            str+=img_name+"+";
            document.getElementById(`${index}-button`).checked = false;

        }
    }
}





export default function Photos(){
    const [photos, setPhotos] = useState({})
    const {id} = useParams();
    const sendToCart = () => {
        let isChecked = false
        const pArray = Array.from(photos)
        for (let i = 0; i < pArray.length; ++i){
            let index = pArray[i]['fileName'];
            if (document.getElementById(`${index}-button`).checked) {
                isChecked=true;
                break;
            }
        }
        if (!isChecked){
            alert("No pictures are selected")
            return;
        }
        const type = document.getElementById('types').value
        const user = localStorage.getItem('email')
        if (!user){
            return;
        }
        let price = 0;
        if (type === "deck") {
            price = 3
        } else if (type === "cup") {
            price = 5;
        } else if (type === "small picture") {
            price = 1;
        } else if (type === "medium picture") {
            price = 2;
        } else if (type === "large picture") {
            price = 3;
        } else if (type === "framed small picture") {
            price = 2;
        } else if (type === "framed medium picture") {
            price = 3;
        } else if (type === "framed large picture") {
            price = 4;
        } else if (type === "poster") {
            price = 3;
        } else if (type === "framed picture") {
            price = 6;
        } else if (type === "t-shirt") {
            price = 15;
        }
        else if (type === "album"){
            let str=""
            document.getElementById('')
            for (let i = 0; i < pArray.length; ++i) {
                const index = pArray[i]['fileName'];
    
                if (document.getElementById(`${index}-button`).checked) {
                    const image = document.getElementById(`${index}-pic`).src;
                    var img_name = image.replace(/^.*[\\\/]/, ''); 
                    str+=img_name+"|";
                    document.getElementById(`${index}-button`).checked = false;
    
                }
            }
            window.location.href = 'albumForm.php?str='+str;
            return;
        }
        for (let i = 0; i < pArray.length; ++i) {
            const index = pArray[i];
           
            

            if (document.getElementById(`${index['fileName']}-button`).checked) {
                const cartItem = {
                    "userName":localStorage.getItem('email'),
                    "fileName": index['fileName'],
                    "type": type,
                    "price":price
                }
    
                const requestOptions = {
                    method: 'POST',
                    body: JSON.stringify(cartItem),
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization' : `Bearer ${localStorage.getItem('token')}`
                    }
                };
    
                fetch(`/api/items/cartItems/create`,requestOptions)
                .then((res) => res.json())
                .then((res) => {
                    console.log(res)
                }).catch(error => {console.error(error)})
    
                document.getElementById(`${index['fileName']}-button`).checked = false;
            }
        }
        alert("Items are sent to your cart.")
        
    }
    useEffect(() => {
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
            }
        }; 
        fetch(`/api/groups/${id}/photos`,requestOptions)
            .then((res => res.json()))
            .then(res => {
                console.log(res)
                setPhotos(res)
            }).catch(error => {console.error(error)})
    }
    ,[])

    return (
        <div>
            <div className="top-container">

            <button className="order-sender" onClick={downloadImages}>Download Images</button>
            <button className="order-sender" onClick={sendToCart}>Send to Cart</button>
            <select name="types" id="types">
                <option value="cup">Cup</option>
                <option value="t-shirt">T-Shirt</option>
                <option value="deck">Deck</option>
                <option value="small picture">Picture(small)</option>
                <option value="medium picture">Picture(medium)</option>
                <option value="large picture">Picture(large)</option>
                <option value="framed small picture">Framed Picture(small)</option>
                <option value="framed medium picture">Framed Picture(medium)</option>
                <option value="framed large picture">Framed Picture(large)</option>
                <option value="album">Album</option>
                <option value="poster">Poster</option>
            </select>
            <input className="orders" type="file" name="file" id="file"/>
            <input className="orders" type="button" id="btn_uploadfile" value="Upload" onClick={ () => uploadFile(id)}></input>
            </div>
            <div className="photos">

            {Array.from(photos).map(
                photo => 
                <div>
                    <input type="checkbox" id={`${photo["fileName"]}-button`}/>
                    <label htmlFor={`${photo['fileName']}-button`}> 
                    <img id={`${photo['fileName']}-pic`} src={`/api/uploads/${photo['fileName']}.${photo['fileExtension']}`}/>
                    </label>
                </div>
            )}
            </div>
        </div>
    )
}