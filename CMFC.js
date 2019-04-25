
// using "pageshow" jquery mobile event for doc ready since pageCreate() has a bug w/ select menus - https://github.com/jquery/jquery-mobile/issues/1055
$(document).bind("pageshow", function(){
	
	//identifiers
	var canvas = $("#score")[0],
		//canvasOffset = $("#score").offset(), //returns offset coordinates of staff
		canvasSlider = $('#canvas-slider'),
		selectNoteName = $('#select-note-name'),
		selectNoteOctave = $('#select-note-octave'),
		selectNoteAccidental = $('#select-note-accidental'),
		selectNoteDuration = $('#select-note-duration');
		
	//standard lines needed for vexflow with canvas we have the option of saving as a .png or .jpg
	var renderer = new Vex.Flow.Renderer(canvas, Vex.Flow.Renderer.Backends.CANVAS);
	//context for vexflow staff
	var ctx = renderer.getContext();
	
	var staff, 
		formatter, 
		voice, 
		noteOffsetLeft, 
		tickIndex = 0, 
		noteIndex = 0, 
		numBeats = 4, //needs to be user input enabled = $('#select-time-signature'); perhaps
		beatValue = 4, 
		cursorHeight = 150,
		sumTicks = 0;
	// create notes array for storing music score in vexflow format
	var notes = new Array();
	processStave();
	highlightNote();
	drawStave();
	
	// set default values for select menus
	var selectNoteName = $("select#select-note-name");
	selectNoteName[0].selectedIndex = 2;
	selectNoteName.selectmenu("refresh");
	
	var selectNoteAccidental = $("select#select-note-accidental");
	selectNoteAccidental[0].selectedIndex = 0;
	selectNoteAccidental.selectmenu("refresh");

	var selectNoteOctave = $("select#select-note-octave");
	selectNoteOctave[0].selectedIndex = 2;
	selectNoteOctave.selectmenu("refresh");
	
	var selectNoteDuration = $("select#select-note-duration");
	selectNoteDuration[0].selectedIndex = 2;
	selectNoteDuration.selectmenu("refresh");
	
	// click events
	canvas.addEventListener("click", scoreOnClick, false);

	// updates canvas offset position on resize event for canvas mouse clicks 
	$(window).bind( "throttledresize", setCanvasOffset );

//-------SLIDER---------------------------------------
	// canvas slider used to translate() the origin of the canvas for horizontal scrolling
	canvasSlider.change(function() {

		ctx.clear();
		
		// reset transformation
		ctx.setTransform(1,0,0,1,0,0); //this controls the orientation of the stave
		ctx.translate(-1*canvasSlider.val(),0); //the negative is so if you stroll right the stave scrolls to the left 
		
		//highlightNote();
		drawStave();

		if (notes.length > 0) { //redraws notes after coordinates have been updated
			drawNotes();
		}
	});
//----------------------------------------------------

	$("#addNote").click(function () {
		var vexNote = parseNoteInput();
		addNote(vexNote);
	});
	
	$('#deleteNote').click(function() {
		deleteNote();
	});
	
	// functions
	function scoreOnClick(e) {
		//alert("scoreOnClick reached");
		// if notes exist enable canvas click event
		if (notes.length > 0) {
			
			var x, y;

			if (e.pageX != undefined && e.pageY != undefined) {
				x = e.pageX;
				y = e.pageY;
			}
			else {
				x = e.clientX + document.body.scrollLeft +
						document.documentElement.scrollLeft;
				y = e.clientY + document.body.scrollTop +
						document.documentElement.scrollTop;
			}
			
			x -= canvasOffset.left;
			y -= canvasOffset.top;

			findNote(x);
			
			ctx.clear();
			processStave();
			processNotes();
			highlightNote();
			drawStave();
			drawNotes();
		}
	}
	
	// finds note on the canvas based on x coordinate value and sets tickIndex and noteIndex accordingly
	function findNote(xcord) {
		//alert("findNote reached");
		if (formatter.tickContexts.map[tickIndex] == undefined) {
			tickIndex -= 4096;//notes[notes.length-1].ticks;
		}			
		
		var dif = canvas.width;
		
		// set tickIndex for note
		for (var note in formatter.tickContexts.map){

			// skip bar notes in note array
			if (formatter.tickContexts.map[note].maxTicks == 0) {
				continue;
			}
		
			if (Math.abs( noteOffsetLeft + formatter.tickContexts.map[note].x + formatter.tickContexts.map[tickIndex].width - canvasSlider.val() - xcord) < dif) {
				dif = Math.abs( noteOffsetLeft + formatter.tickContexts.map[note].x + formatter.tickContexts.map[tickIndex].width - canvasSlider.val() - xcord);
				tickIndex = note;
			}
		}

		// if user clicks for a new note (anything to the right of the last existing note)
		if ((noteOffsetLeft + formatter.tickContexts.map[tickIndex].x + formatter.tickContexts.map[tickIndex].width + 30 - canvasSlider.val() - xcord) < 0) {

			tickIndex = 0;
			
			for (var i=0; i <= notes.length-1; i++) {
				tickIndex += 4096;
			}
			noteIndex = notes.length;
		}
	
		// set noteIndex for 'notes' array based on tickIndex 'map' object
		var i = 0;
		
		for (var note in formatter.tickContexts.map){
		
			if ( tickIndex == note) {
				noteIndex = i;
				break;
			}
			i++;
		}
	}
	
	//making note object consisting of user input
	function parseNoteInput() {
		//alert("parseNoteInput reached");
		//if theres an acc put it in the obj if not fuck it
		var note_acc = (selectNoteAccidental.val() != "none") ? selectNoteAccidental.val() : "";
								//A,B,C,D,E,D,G as lowercase 		5 for the 5th octave
		var noteObj = { keys: [selectNoteName.val().toLowerCase() + note_acc + "/" + selectNoteOctave.val()],
						duration: selectNoteDuration.val(),
						accidental: selectNoteAccidental.val() };
		
		return noteObj;
	}
	
	function addNote(staveNoteObj) {
		//alert("addNote reached");
		// update to work for editing notes but not adding notes
		
			// edit existing note
			if (noteIndex <= notes.length-1) {
				
				if (staveNoteObj.accidental == "none" ) {
					notes.splice(noteIndex, 1, new Vex.Flow.StaveNote(staveNoteObj));
				}
				else {
					notes.splice(noteIndex, 1,new Vex.Flow.StaveNote(staveNoteObj).addAccidental(0, new Vex.Flow.Accidental(selectNoteAccidental.val())) );
				}
			}
			// add new note
			else {
				if (stave.width < 2000) {
					// add new note to end of notes array
					if (staveNoteObj.accidental == "none" ) {
						notes.push(new Vex.Flow.StaveNote(staveNoteObj));
					}
					else {
						//add note with accidental
						notes.push( new Vex.Flow.StaveNote(staveNoteObj).addAccidental(0, new Vex.Flow.Accidental(selectNoteAccidental.val())) );
					}
					
					noteIndex = notes.length;
				}
				else {
					//alert("Cannot add anymore notes! You have reached the max number of notes for this demo.");
				}
				
			}
			//after we add the note to the array redraw entire stave with new notes array
			ctx.clear();
			processStave();
			processNotes();
			drawStave();
			drawNotes();
			
			if (noteIndex > notes.length-1) {
				// calculate note index for map array
				tickIndex = 0;
				for (var i=0; i <= notes.length-1; i++) {
				
					tickIndex += 4096;
				}
			}
			
			highlightNote();
			
			// update max value for slider
			if (stave.width > 550) {
				canvasSlider.attr('max',stave.width);
				canvasSlider.slider('refresh');
			}
	}
	
	function deleteNote() {
		//alert("deleteNote reached");
		notes.splice(noteIndex, 1);
		
		ctx.clear();
		processStave();
		drawStave();
		if (notes.length > 0) {
			processNotes();
			drawNotes();
		}
		
		highlightNote();
		
		// update max value for slider
		if (stave.width > 550) {
			canvasSlider.attr('max',stave.width);
			canvasSlider.slider('refresh');
		}
	}
	
	function processStave() {
		//alert("processStave reached");
		var staveSize;

		// set stave width
		if (notes.length < 6) {
			staveSize = 550;
		}
		else {
			// about 85 pixels per note
			staveSize = (notes.length+1) * 85;
		}
		stave = new Vex.Flow.Stave(10, 20, staveSize);

		stave.addClef("treble"); //find a way to make this common time
		
		// add time
		stave.addTimeSignature(numBeats + "/" + beatValue);
		
		// calc offset for first note - accounts for pixels used by treble clef & time signature & key signature
		noteOffsetLeft = 72.94535 + 15;
	}
	
	function processNotes() {
		//alert("processNotes reached");
		// add new measure if necessary
		processMeasures();
		
		// create a voice in 4/4
		voice = new Vex.Flow.Voice({
			num_beats: numBeats,
			beat_value: beatValue,
			resolution: Vex.Flow.RESOLUTION
		});
		
		// turn off tick counter
		voice.setStrict(false);
		
		// Add notes to voice
		voice.addTickables(notes);
				
		// Format and justify the notes
		var voiceSize = notes.length * 85 - 50;
		
		formatter = new Vex.Flow.Formatter().joinVoices([voice]).format([voice], voiceSize);
	}
	
	function highlightNote() {
		//alert("highlightNote reached");
		ctx.fillStyle = "rgba(200,0,0,0.4)";
		
		// if notes exist
		if (notes.length > 0) {

			// when adding a new note vs. editing an existing note draw the cursor for next new note 
			//(the tickIndex will be undefined in map object for a new note)
			if (formatter.tickContexts.map[tickIndex] == undefined) {
				var tempIndex = tickIndex - 4096;
				ctx.fillRect(noteOffsetLeft + formatter.tickContexts.map[tempIndex].x + 60, 10, 16.5, cursorHeight);
			}
			else {
				ctx.fillRect(noteOffsetLeft + formatter.tickContexts.map[tickIndex].x - 10, 10, formatter.tickContexts.map[tickIndex].width 
					+ formatter.tickContexts.map[tickIndex].padding*2, cursorHeight);
			}
		}
		else {
			ctx.fillRect(noteOffsetLeft, 10, 16, cursorHeight);
		}
		
		ctx.fillStyle = "#000";
	}
	
	function processMeasures() {
		//alert("processMeasures");
		// sum ticks and add new measures when neccessary
		var sumTicks = 0;
		var totalTicksPerMeasure = 1024 * numBeats * beatValue;

		for ( var i = 0; i < notes.length; i++) {
			if (notes[i].duration == "b") {
				sumTicks = 0;
				continue;
			}
		 
			if (sumTicks == totalTicksPerMeasure) {
				notes.splice(i,0,new Vex.Flow.BarNote());
				noteIndex++;
				sumTicks = 0;
			}
			if (notes[i].duration == "q") sumTicks += 4096;
			else if (notes[i].duration == "h") sumTicks += 8192;
			else if (notes[i].duration == "w") sumTicks += 16384;
			else if (notes[i].duration == "16") sumTicks += 1024;
			else sumTicks += 2048;
		}
	}
	
	function drawStave() {
		stave.setContext(ctx).draw();
	}
	
	function drawNotes() {
		voice.draw(ctx, stave);
	}
	 
	function setCanvasOffset() {
	canvasOffset = $("#score").offset();
	}
});