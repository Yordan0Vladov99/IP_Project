import React from "react";
import Photos from "../Photos/Photos";
import Children from "./Children/Children";
import Path from "./Path/Path";
import './Group.css'

class Group extends React.Component {
    render(){
        return(
            <div>
                <Path/>
                <Children/>
                <Photos/>
            </div>
        )
    }
}

export default Group;