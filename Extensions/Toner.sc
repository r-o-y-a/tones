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
		var p1, p2, p3;
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
		patterns.put(\pattern1, p1);
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
		patterns.put(\pattern2, p2);
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
		patterns.put(\pattern3, p3);
		p3.play;


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
			}
		);
*/

		textTone.postln;
		^textTone;
	}

    getPresets { | textTone |
		presets = [Dictionary.new, Dictionary.new, Dictionary.new];
		//case {if("admiring" or: {"amused"})} {alskdjf}
		//case {if("admiring" or: {"amused"})} {alskdjf}
		switch(textTone,
			"admiring", {
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
			},
			"amused", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"eager", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"excited", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"grateful", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"joyful", {
				presets[0].put(\rate, [1.5]);
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

				presets[2].put(\rate, [2.7, 2.8, 2.9]);
				presets[2].put(\amp, [0.7]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 0.4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0);
				presets[2].put(\delayTime, 0);
				presets[2].put(\decayTime, 0);
			},
			"loving", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"approving", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"angry", {
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
			},
			"annoyed", {
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
			},
			"disappointed", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"disapproving", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"repulsed", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
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
			},
			"mournful", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"sympathetic", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"worried", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"remorseful", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"embarassed", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"fearful", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"confused", {
				presets[0].put(\rate, [1, 1.2, 1.4, 1.6]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 1);
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
			},
			"relieved", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"aware", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"confident", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"curious", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
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
			},
			"optimistic", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
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
			}
		);
		^presets;
	}
}
