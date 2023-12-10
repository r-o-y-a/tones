Toner {

	var synthdef;
	var presets, patterns;

    *new {
        arg synthDef;
		^super.newCopyArgs(synthDef);
    }

	t { | text |
		var textTone = this.getToneFromText(text);
		var presets = this.getPresets(textTone);
		if (presets == nil or: { presets == "" }) {
			// do nothing
		} {
			var patterns = this.playPatterns(presets, synthdef);
			^patterns;
		}
	}

	/* -- private methods -- */

	playPatterns { | presets, synthdef |
		var p1, p2, p3, p4, p5;
		var patterns = Dictionary.new;

		p1 = Pdef(\pSample1,
			Pmono(
				synthdef,
				\bufnum, ~buffers[0].bufnum,
				\rate, Pseq(presets[0].at(\rate), inf),
				\lfoFreq, Pseq([ Pfunc { presets[0].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[0].at(\amp), inf),
				\filterFreq, presets[0].at(\filterFreq),
				\filterRes, presets[0].at(\filterRes),
				\loop, presets[0].at(\loop),
				\reverbMix, presets[0].at(\reverbMix),
				\pitchShift, presets[0].at(\pitchShift),
				\delayMaxTime, presets[0].at(\delayMaxTime),
				\delayTime, presets[0].at(\delayTime),
				\pitchShift, presets[0].at(\pitchShift)
		    )
		);
		patterns.put(\p1, p1);
		p1.play;

		p2 = Pdef(\pSample2,
			Pmono(
				synthdef,
				\bufnum, ~buffers[1].bufnum,
				\rate, Pseq(presets[1].at(\rate), inf),
				\lfoFreq, Pseq([ Pfunc { presets[1].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[1].at(\amp), inf),
				\filterFreq, presets[1].at(\filterFreq),
				\filterRes, presets[1].at(\filterRes),
				\loop, presets[1].at(\loop),
				\reverbMix, presets[1].at(\reverbMix),
				\pitchShift, presets[1].at(\pitchShift),
				\delayMaxTime, presets[1].at(\delayMaxTime),
				\delayTime, presets[1].at(\delayTime),
				\pitchShift, presets[1].at(\pitchShift)
		    )
		);
		patterns.put(\p2, p2);
		p2.play;

		p3 = Pdef(\pSample3,
			Pmono(
				synthdef,
				\bufnum, ~buffers[2].bufnum,
				\rate, Pseq(presets[2].at(\rate), inf),
				\lfoFreq, Pseq([ Pfunc { presets[2].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[2].at(\amp), inf),
				\filterFreq, presets[2].at(\filterFreq),
				\filterRes, presets[2].at(\filterRes),
				\loop, presets[2].at(\loop),
				\reverbMix, presets[2].at(\reverbMix),
				\pitchShift, presets[2].at(\pitchShift),
				\delayMaxTime, presets[2].at(\delayMaxTime),
				\delayTime, presets[2].at(\delayTime),
				\pitchShift, presets[2].at(\pitchShift)
		    )
		);
		patterns.put(\p3, p3);
		p3.play;


		p4 = Pdef(\pSample4,
			Pmono(
				synthdef,
				\bufnum, ~buffers[3].bufnum,
				\rate, Pseq(presets[3].at(\rate), inf),
				\lfoFreq, Pseq([ Pfunc { presets[3].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[3].at(\amp), inf),
				\filterFreq, presets[3].at(\filterFreq),
				\filterRes, presets[3].at(\filterRes),
				\loop, presets[3].at(\loop),
				\reverbMix, presets[3].at(\reverbMix),
				\pitchShift, presets[3].at(\pitchShift),
				\delayMaxTime, presets[3].at(\delayMaxTime),
				\delayTime, presets[3].at(\delayTime),
				\pitchShift, presets[3].at(\pitchShift)
		    )
		);
		patterns.put(\p4, p4);
		p4.play;


		p5 = Pdef(\pSample5,
			Pmono(
				synthdef,
				\bufnum, ~buffers[4].bufnum,
				\rate, Pseq(presets[4].at(\rate), inf),
				\lfoFreq, Pseq([ Pfunc { presets[4].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[4].at(\amp), inf),
				\filterFreq, presets[4].at(\filterFreq),
				\filterRes, presets[4].at(\filterRes),
				\loop, presets[4].at(\loop),
				\reverbMix, presets[4].at(\reverbMix),
				\pitchShift, presets[4].at(\pitchShift),
				\delayMaxTime, presets[4].at(\delayMaxTime),
				\delayTime, presets[4].at(\delayTime),
				\pitchShift, presets[4].at(\pitchShift)
		    )
		);
		patterns.put(\p5, p5);
		p5.play;



		^patterns;
	}

	getToneFromText { | text |
		var x, textTone;

		var path = PathName(thisProcess.nowExecutingPath).pathOnly;
		var apikey = File.readAllString(path ++ "apikey.scd");


		x = "curl -X POST https://api.sapling.ai/api/v1/tone -H \"Content-Type: application/json\" \ -d \'{\"key\":\"" ++apikey++"\", \"text\":\"" ++ text ++ "\"}\'";
		x = x.replace("\n", replace:"");
		x = x.unixCmdGetStdOut;
		x.postln;
		x = x.escapeChar($");
		x = x.replace("\\", replace:"");
		x = x.parseJSON;
		textTone = x["results"][0][0][1];
		//d["results"][0][1][1].postln; // secondary tone
		//d["results"][0][2][1].postln; // tertiary tone

/*
		// code for when i get rate-limited from the API :I
		switch(text,
			"It was a cold winter day.", {
				textTone = "sad";
			},
			"But the sun was shining.", {
				textTone = "neutral";
			},
			"How nice everything looked on my way to school, I thought.", {
				textTone = "admiring";
			},
			"Suddenly, a crow flew across the horizon and startled me.", {
				textTone = "surprised";
			},
			"It went away but then came back again, flying in my face.", {
				textTone = "annoyed";
			},
			"Go away! I said.", {
				textTone = "angry";
			},
			"admiring", {
				textTone = "admiring";
			},
			"amused", {
				textTone = "amused";
			},
			"eager", {
				textTone = "eager";
			},
			"excited", {
				textTone = "excited";
			},
			"grateful", {
				textTone = "grateful";
			},
			"joyful", {
				textTone = "joyful";
			},
			"loving", {
				textTone = "loving";
			},
			"approving", {
				textTone = "approving";
			},
			"angry", {
				textTone = "angry";
			},
			"annoyed", {
				textTone = "annoyed";
			},
			"disappointed", {
				textTone = "disappointed";
			},
			"disapproving", {
				textTone = "disapproving";
			},
			"repulsed", {
				textTone = "repulsed";
			},
			"sad", {
				textTone = "sad";
			},
			"mournful", {
				textTone = "mournful";
			},
			"sympathetic", {
				textTone = "sympathetic";
			},
			"worried", {
				textTone = "worried";
			},
			"remorseful", {
				textTone = "remorseful";
			},
			"embarassed", {
				textTone = "embarassed";
			},
			"fearful", {
				textTone = "fearful";
			},
			"confused", {
				textTone = "confused";
			},
			"relieved", {
				textTone = "relieved";
			},
			"aware", {
				textTone = "aware";
			},
			"confident", {
				textTone = "confident";
			},
			"curious", {
				textTone = "curious";
			},
			"neutral", {
				textTone = "neutral";
			},
			"optimistic", {
				textTone = "optimistic";
			},
			"surprised", {
				textTone = "surprised";
			}
		);
*/

		textTone.postln;
		^textTone;
	}

    getPresets { | textTone |
		presets = [Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new];
		//case {if("admiring" or: {"amused"})} {alskdjf}
		//case {if("admiring" or: {"amused"})} {alskdjf}
		switch(textTone,
			"admiring", {
				presets[0].put(\rate, [0]);
				presets[0].put(\amp, [0.4]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [1, 2, 1.5, 3, 1]);
				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 1500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"amused", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"eager", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"excited", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"grateful", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"joyful", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"loving", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"approving", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"angry", {
				presets[0].put(\rate, [0.5, 0.2, 0.4]);
				presets[0].put(\amp, [0.8]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [1]);
				presets[1].put(\amp, [0]);
				presets[1].put(\filterFreq, 3000);
				presets[1].put(\filterRes, 4);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [0.2]);
				presets[2].put(\amp, [0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 6);

				presets[3].put(\rate, [1]);
				presets[3].put(\amp, [0.2]);
				presets[3].put(\filterFreq, 3000);
				presets[3].put(\filterRes, 4);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [1]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 3000);
				presets[4].put(\filterRes, 4);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"annoyed", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [1]);
				presets[1].put(\amp, [0]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [5, 6, 8]);
				presets[2].put(\amp, [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0.1);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [1]);
				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\rate, [1]);
				presets[4].put(\amp, [0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.8, 0.5, 0.4, 0.3, 0.2, 0.1]);
				presets[4].put(\filterFreq, 2000);
				presets[4].put(\filterRes, 4);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);

			},
			"disappointed", {
				presets[0].put(\rate, [2.7, 2.5, 2.9, 2.6]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [1]);
				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [1]);
				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\rate, [1]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 1500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"disapproving", {
				presets[0].put(\rate, [0.5, 0.2, 0.4]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [1]);
				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 3000);
				presets[1].put(\filterRes, 4);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [0.2]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 6);

				presets[3].put(\rate, [1]);
				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\rate, [1]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 1500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"repulsed", {
				presets[0].put(\rate, [0.5, 0.2, 0.4]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [1]);
				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 3000);
				presets[1].put(\filterRes, 4);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [0.2]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 6);

				presets[3].put(\rate, [1]);
				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\rate, [1]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 1500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"sad", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [1]);
				presets[1].put(\amp, [0]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [1]);
				presets[3].put(\amp, [0.1]);
				presets[3].put(\filterFreq, 1500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"mournful", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"sympathetic", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"worried", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"remorseful", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"embarassed", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"fearful", {
				presets[0].put(\rate, [2.7, 2.5, 2.9, 2.6]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [1]);
				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"confused", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\filterFreq, 1500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"relieved", {
				presets[0].put(\rate, [2.7, 2.5, 2.9, 2.6]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [1]);
				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [1]);
				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\rate, [1]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 1500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"aware", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"confident", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"curious", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"neutral", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0.1]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0.1]);
				presets[3].put(\filterFreq, 1500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"optimistic", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"surprised", {
				presets[0].put(\rate, [3.3, 3.7, 3.1, 3.9]);
				presets[0].put(\amp, [0.5, 0.4, 0.2]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [2.9]);
				presets[2].put(\amp, [0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			{ |default|
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			}
		);
		^presets;
	}
}
