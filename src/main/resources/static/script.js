let state = null;

let kitchen_light = document.getElementById("kitchen-light");
let bathroom_curtain = document.getElementById("bathroom-curtain");
let bathroom_light = document.getElementById("bathroom-light");
let bedroom_light = document.getElementById("bedroom-light");
let bedroom_curtain = document.getElementById("bedroom-curtain");
let living_room_light = document.getElementById("living-room-light");
let living_room_television = document.getElementById("living-room-television");
let basement_light = document.getElementById("basement-light");
let central_heating_target = document.getElementById("central-heating-target");
let central_heating_current  = document.getElementById("central-heating-current");

let lightOnHTML = "<span class='text-success'>włączone</span>"
let lightOffHTML = "<span class='text-danger'>wyłączone</span>"
let coveringHTML = "<span class='text-warning'>zasunięte</span>"
let uncoveringHTML = "<span class='text-primary'>odsunięte</span>"

function updateView(){
    kitchen_light.innerHTML = state.kitchen.light.on ? lightOnHTML : lightOffHTML;
    bathroom_light.innerHTML = state.bathroom.light.on ? lightOnHTML : lightOffHTML;
    bedroom_light.innerHTML = state.bedroom.light.on ? lightOnHTML : lightOffHTML;
    living_room_light.innerHTML = state.livingRoom.light.on ? lightOnHTML : lightOffHTML;
    basement_light.innerHTML = state.basement.light.on ? lightOnHTML : lightOffHTML;

    bathroom_curtain.innerHTML = state.bathroom.curtain.covering ? coveringHTML : uncoveringHTML;
    bedroom_curtain.innerHTML = state.bedroom.curtain.covering ? coveringHTML : uncoveringHTML;

    central_heating_target.innerHTML = state.basement.centralHeating.targetTemp + " \xB0C";
    central_heating_current.innerHTML = state.basement.centralHeating.currentTemp + " \xB0C";

    if (state.livingRoom.television.isOn){
        living_room_television.innerHTML = state.livingRoom.television.channel;
    } else{
        living_room_television.innerHTML = "<span class='text-danger'>wyłączony</span>";
    }
}

window.setInterval(() => {
    fetch("/state")
        .then(response => response.json())
        .then(data => {console.log(data); state = data; updateView();})
}, 100);