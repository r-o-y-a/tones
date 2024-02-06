Toner {
	var synthdef, synthdef2, runOffline = false;
	var presets, secondaryPresets, allPresets, patterns;
	var currentGroup, previousGroup;

    *new {
        arg synthDef, synthDef2, runOffline;
		^super.newCopyArgs(synthDef, synthDef2, runOffline);
    }

	t { | text, hideSecondary |
		var allPresets, patterns, playSecondary;
		var primaryTextTone, secondaryTextTone;
		var textTones = this.getTonesFromText(text, runOffline, hideSecondary);

		var tones = textTones.split($,);
		primaryTextTone = tones[0];
		secondaryTextTone = tones[1];

		allPresets = this.getPresets(primaryTextTone, secondaryTextTone);

		if (allPresets != nil) {

			patterns = this.playRoutine(allPresets, synthdef, synthdef2, playSecondary);

			^patterns;
		}
	}

	/* -- private methods -- */


    playRoutine { | allPresets, synthdef, synthdef2, playSecondary |

		if (~currentSynths.notNil) {

				var newSynths, newGroup;
				var endTime = 4;
				var steps = 100;
				var stepTime = endTime / steps;

				currentGroup.postln;


				// TOOD: use the following example for accessing the synths
				// from: https://doc.sccode.org/Classes/Group.html
				/*
				(
				r = Routine({
				inf.do({
				l.do({ arg node;
				node.set("freq",rrand(10,120));
				1.0.wait;
				});
				})
				});

				r.play;
				)*/

			previousGroup = currentGroup.copy;


			// decrease volume of current group
			{
				steps.do { |i|
					var currentAmp = 1.0 - (i / steps);

					previousGroup.set(\amp, currentAmp);

					stepTime.wait;
				};

				previousGroup.free;

				"previous group released".postln;


				//currentGroup.set(\amp, 0);
				//currentGroup.set(\gate, 0);

				//currentGroup.release;
			}.fork();


			// create the new synths to replace the currently playing
				newSynths = this.createSynths(allPresets, synthdef, synthdef2, playSecondary);
				newGroup = newSynths.at(\group);
				~currentSynths = newSynths;
				newGroup.postln;


        } {
            // if no currently playing synths, create first ones
			~currentSynths = this.createSynths(allPresets, synthdef, synthdef2, playSecondary);
			"created first synths".postln;
		};

		^~currentSynths;
	}


	createSynths { | allPresets, synthdef, synthdef2, playSecondary |
		var p1, p2, p3, p4, p5;
		//var p2nd1, p2nd2;
		var patterns = Dictionary.new;
		var primaryPresets = allPresets[0];
		var secondaryPresets = allPresets[1];
		currentGroup = Group.after;


		p1 = Pdef(\pSample1,
			Pmono(
				synthdef,
				\group, currentGroup,
				\bufnum, ~buffers[0].bufnum,
				\rate, Pseq(~rate[0], inf),
				\lfoFreq, Pseq([ Pfunc { primaryPresets[0].at(\lfoPseqValue) }], inf),
				\amp, Pseq(primaryPresets[0].at(\amp), inf),
				\filterFreq, primaryPresets[0].at(\filterFreq),
				\filterRes, primaryPresets[0].at(\filterRes),
				\loop, primaryPresets[0].at(\loop),
				\reverbMix, primaryPresets[0].at(\reverbMix),
				\pitchShift, primaryPresets[0].at(\pitchShift),
				\delayMaxTime, primaryPresets[0].at(\delayMaxTime),
				\delayTime, primaryPresets[0].at(\delayTime),
				\pitchShift, primaryPresets[0].at(\pitchShift)
		    )
		);
		patterns.put(\p1, p1);
		p1.play;


		p2 = Pdef(\pSample2,
			Pmono(
				synthdef,
				\group, currentGroup,
				\bufnum, ~buffers[1].bufnum,
				\rate, Pseq(~rate[1], inf),
				\lfoFreq, Pseq([ Pfunc { primaryPresets[1].at(\lfoPseqValue) }], inf),
				\amp, Pseq(primaryPresets[1].at(\amp), inf),
				\filterFreq, primaryPresets[1].at(\filterFreq),
				\filterRes, primaryPresets[1].at(\filterRes),
				\loop, primaryPresets[1].at(\loop),
				\reverbMix, primaryPresets[1].at(\reverbMix),
				\pitchShift, primaryPresets[1].at(\pitchShift),
				\delayMaxTime, primaryPresets[1].at(\delayMaxTime),
				\delayTime, primaryPresets[1].at(\delayTime),
				\pitchShift, primaryPresets[1].at(\pitchShift)
		    )
		);
		patterns.put(\p2, p2);
		p2.play;

		p3 = Pdef(\pSample3,
			Pmono(
				synthdef,
				\group, currentGroup,
				\bufnum, ~buffers[2].bufnum,
				\rate, Pseq(~rate[2], inf),
				\lfoFreq, Pseq([ Pfunc { primaryPresets[2].at(\lfoPseqValue) }], inf),
				\amp, Pseq(primaryPresets[2].at(\amp), inf),
				\filterFreq, primaryPresets[2].at(\filterFreq),
				\filterRes, primaryPresets[2].at(\filterRes),
				\loop, primaryPresets[2].at(\loop),
				\reverbMix, primaryPresets[2].at(\reverbMix),
				\pitchShift, primaryPresets[2].at(\pitchShift),
				\delayMaxTime, primaryPresets[2].at(\delayMaxTime),
				\delayTime, primaryPresets[2].at(\delayTime),
				\pitchShift, primaryPresets[2].at(\pitchShift)
		    )
		);
		patterns.put(\p3, p3);
		p3.play;


		p4 = Pdef(\pSample4,
			Pmono(
				synthdef,
				\group, currentGroup,
				\bufnum, ~buffers[3].bufnum,
				\rate, Pseq(~rate[3], inf),
				\lfoFreq, Pseq([ Pfunc { primaryPresets[3].at(\lfoPseqValue) }], inf),
				\amp, Pseq(primaryPresets[3].at(\amp), inf),
				\filterFreq, primaryPresets[3].at(\filterFreq),
				\filterRes, primaryPresets[3].at(\filterRes),
				\loop, primaryPresets[3].at(\loop),
				\reverbMix, primaryPresets[3].at(\reverbMix),
				\pitchShift, primaryPresets[3].at(\pitchShift),
				\delayMaxTime, primaryPresets[3].at(\delayMaxTime),
				\delayTime, primaryPresets[3].at(\delayTime),
				\pitchShift, primaryPresets[3].at(\pitchShift)
		    )
		);
		patterns.put(\p4, p4);
		p4.play;


		p5 = Pdef(\pSample5,
			Pmono(
				synthdef,
				\group, currentGroup,
				\bufnum, ~buffers[4].bufnum,
				\rate, Pseq(~rate[4], inf),
				\lfoFreq, Pseq([ Pfunc { primaryPresets[4].at(\lfoPseqValue) }], inf),
				\amp, Pseq(primaryPresets[4].at(\amp), inf),
				\filterFreq, primaryPresets[4].at(\filterFreq),
				\filterRes, primaryPresets[4].at(\filterRes),
				\loop, primaryPresets[4].at(\loop),
				\reverbMix, primaryPresets[4].at(\reverbMix),
				\pitchShift, primaryPresets[4].at(\pitchShift),
				\delayMaxTime, primaryPresets[4].at(\delayMaxTime),
				\delayTime, primaryPresets[4].at(\delayTime),
				\pitchShift, primaryPresets[4].at(\pitchShift)
		    )
		);
		patterns.put(\p5, p5);
		p5.play;


		// --- secondary synths ----

		/*
		if (playSecondary == 1) {

			p2nd1 = Pdef(\pSample2nd1,
				Pmono(
					synthdef2,
					\group, currentGroup,
					\bufnum, ~buffers[5].bufnum,
					\rate, Pseq(~secondaryRate[0], inf),
					\lfoFreq, Pseq([ Pfunc { secondaryPresets[0].at(\lfoPseqValue) }], inf),
					\amp, Pseq(secondaryPresets[0].at(\amp), inf),
					\filterFreq, secondaryPresets[0].at(\filterFreq),
					\filterRes, secondaryPresets[0].at(\filterRes),
					\loop, secondaryPresets[0].at(\loop),
					\reverbMix, secondaryPresets[0].at(\reverbMix),
					\pitchShift, secondaryPresets[0].at(\pitchShift),
					\delayMaxTime, secondaryPresets[0].at(\delayMaxTime),
					\delayTime, secondaryPresets[0].at(\delayTime),
					\pitchShift, secondaryPresets[0].at(\pitchShift)
				)
			);
			patterns.put(\p2nd1, p2nd1);
			p2nd1.play;


			p2nd2 = Pdef(\pSample2nd2,
				Pmono(
					synthdef2,
					\group, currentGroup,
					\bufnum, ~buffers[6].bufnum,
					\rate, Pseq(~secondaryRate[1], inf),
					\lfoFreq, Pseq([ Pfunc { primaryPresets[1].at(\lfoPseqValue) }], inf),
					\amp, Pseq(primaryPresets[1].at(\amp), inf),
					\filterFreq, primaryPresets[1].at(\filterFreq),
					\filterRes, primaryPresets[1].at(\filterRes),
					\loop, primaryPresets[1].at(\loop),
					\reverbMix, primaryPresets[1].at(\reverbMix),
					\pitchShift, primaryPresets[1].at(\pitchShift),
					\delayMaxTime, primaryPresets[1].at(\delayMaxTime),
					\delayTime, primaryPresets[1].at(\delayTime),
					\pitchShift, primaryPresets[1].at(\pitchShift)
				)
			);
			patterns.put(\p2nd2, p2nd2);
			p2nd2.play;
		};
		*/

		patterns.put(\group, currentGroup); // also return the current group

		^patterns;
	}

	getTonesFromApi { | text |
		var x, textTones;

		var path = PathName(thisProcess.nowExecutingPath).pathOnly;
		var cmd = File.readAllString(path ++ "getDataCommand.txt");

		x = (cmd + "\"" ++ text ++ "\"");
		x = x.unixCmdGetStdOut;
		textTones = x.replace("\n", replace:"");

		^textTones;
	}

	getTonesFromText { | text, runOffline, hideSecondary |
		var textTones, b;
		var textTone, secondaryTextTone;


		if (runOffline == false || runOffline.isNil) {
			textTones = this.getTonesFromApi(text);
		};

		// run offline
		if (textTones == nil) {
			switch(text,
				"It was a cold depressing winter day.", {
					textTone = "sadness";
				},
				"But the sun was shining.", {
					textTone = "neutral";
				},
				"How nice everything looked on my way to school, I thought.", {
					textTone = "admiration";
				},
				"Suddenly, a crow flew across the horizon and startled me.", {
					textTone = "surprise";
				},
				"It went away but then came back again, flying in my face. How annoying!", {
					textTone = "annoyance";
				},
				"Go away! I said.", {
					textTone = "anger";
				},
				"It went away this time for good and everything was fine again.", {
					textTone = "relief";
				},
				"admiration", {
					textTone = "admiration";
				},
				"amusement", {
					textTone = "amusement";
				},
				"anger", {
					textTone = "anger";
				},
				"annoyance", {
					textTone = "annoyance";
				},
				"approval", {
					textTone = "approval";
				},
				"caring", {
					textTone = "caring";
				},
				"confusion", {
					textTone = "confusion";
				},
				"curiosity", {
					textTone = "curiosity";
				},
				"desire", {
					textTone = "desire";
				},
				"disappointment", {
					textTone = "disappointment";
				},
				"disapproval", {
					textTone = "disapproval";
				},
				"disgust", {
					textTone = "disgust";
				},
				"embarrassment", {
					textTone = "embarrassment";
				},
				"excitement", {
					textTone = "excitement";
				},
				"fear", {
					textTone = "fear";
				},
				"gratitude", {
					textTone = "gratitude";
				},
				"grief", {
					textTone = "grief";
				},
				"joy", {
					textTone = "joy";
				},
				"love", {
					textTone = "love";
				},
				"nervousness", {
					textTone = "nervousness";
				},
				"optimism", {
					textTone = "optimism";
				},
				"pride", {
					textTone = "pride";
				},
				"realization", {
					textTone = "realization";
				},
				"relief", {
					textTone = "relief";
				},
				"remorse", {
					textTone = "remorse";
				},
				"sadness", {
					textTone = "sadness";
				},
				"surprise", {
					textTone = "surprise";
				},
				"neutral", {
					textTone = "neutral";
				},
			);
		};



		b = NetAddr.new("127.0.0.1", 57121);


		if (hideSecondary == 1) {
			textTones.split($,)[0].postln;
			b.sendMsg("/data", textTones.split($,)[0]);
		} {
			textTones.postln;
			b.sendMsg("/data", textTones);
		};

		^textTones;
	}

	// each tone has a both a primary and secondary preset (for when it is accompanying a primary)
    getPresets { | textTone, secondaryTextTone |
		presets = [Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new];
		secondaryPresets = [Dictionary.new, Dictionary.new];


		// todo: return secondaryPresets[0], and secondaryPresets[1] for all presets


		if (textTone == "admiration") {
				~rate = [[1, 1, 1, 1.5, 1.5, 1, 1.5, 1, 1, 1.5, 1.5, 1.8, 2, 2, 1], [0], [3], [1, 2, 1.5, 3, 1], [3]];

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
		};

		if (secondaryTextTone == "admiration") {
			~secondaryRate = [[1, 1, 1, 1.5, 1.5, 1, 1.5, 1, 1, 1.5, 1.5, 1.8, 2, 2, 1], [0], [3], [1, 2, 1.5, 3, 1], [3]];

			secondaryPresets[0].put(\amp, [0.4]);
			secondaryPresets[0].put(\filterFreq, 1500);
			secondaryPresets[0].put(\filterRes, 3);
			secondaryPresets[0].put(\start, 0);
			secondaryPresets[0].put(\end, 1);
			secondaryPresets[0].put(\loop, 1);
			secondaryPresets[0].put(\lfoPseqValue, 0);
			secondaryPresets[0].put(\reverbMix, 7);
			secondaryPresets[0].put(\pitchShift, 0);
			secondaryPresets[0].put(\delayMaxTime, 0);
			secondaryPresets[0].put(\delayTime, 0);
			secondaryPresets[0].put(\decayTime, 0);

			secondaryPresets[1].put(\amp, [0]);
			secondaryPresets[1].put(\filterFreq, 1500);
			secondaryPresets[1].put(\filterRes, 3);
			secondaryPresets[1].put(\start, 0);
			secondaryPresets[1].put(\end, 1);
			secondaryPresets[1].put(\loop, 1);
			secondaryPresets[1].put(\lfoPseqValue, 0);
			secondaryPresets[1].put(\reverbMix, 1);
			secondaryPresets[1].put(\pitchShift, 0);
			secondaryPresets[1].put(\delayMaxTime, 0);
			secondaryPresets[1].put(\delayTime, 0);
			secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "amusement") {
				~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

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
			};

		if (secondaryTextTone == "amusement") {
			~secondaryRate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

			secondaryPresets[0].put(\amp, [0.5]);
			secondaryPresets[0].put(\filterFreq, 1500);
			secondaryPresets[0].put(\filterRes, 3);
			secondaryPresets[0].put(\start, 0);
			secondaryPresets[0].put(\end, 1);
			secondaryPresets[0].put(\loop, 1);
			secondaryPresets[0].put(\lfoPseqValue, 0.1);
			secondaryPresets[0].put(\reverbMix, 7);
			secondaryPresets[0].put(\pitchShift, 0);
			secondaryPresets[0].put(\delayMaxTime, 0);
			secondaryPresets[0].put(\delayTime, 0);
			secondaryPresets[0].put(\decayTime, 0);

			secondaryPresets[1].put(\amp, [0]);
			secondaryPresets[1].put(\start, 0);
			secondaryPresets[1].put(\end, 1);
			secondaryPresets[1].put(\loop, 1);
			secondaryPresets[1].put(\lfoPseqValue, 0);
			secondaryPresets[1].put(\reverbMix, 1);
			secondaryPresets[1].put(\pitchShift, 0);
			secondaryPresets[1].put(\delayMaxTime, 0);
			secondaryPresets[1].put(\delayTime, 0);
			secondaryPresets[1].put(\decayTime, 0);

		};

		if (textTone == "desire") {
				~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

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
			};


		if (secondaryTextTone == "desire") {
				~secondaryRate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0.1);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "excitement") {
				~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

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
			};

		if (secondaryTextTone == "excitement") {
				~secondaryRate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0.1);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "gratitude") {
			~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

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
			};

		if (secondaryTextTone == "gratitude") {
			~secondaryRate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "joy") {
			~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

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
			};

		if (secondaryTextTone == "joy") {
			~secondaryRate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0.1);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};

		if (textTone == "love") {
				~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

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
			};

		if (secondaryTextTone == "love") {
				~secondaryRate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0.1);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};


		if (textTone == "approval") {
				~rate = [[2, 2, 2, 3, 2, 2, 2, 2, 2, 3, 2, 2, 2, 2, 5], [0, 0, 0, 1.5, 1.5, 1.5, 1.5, 1.5, 1.8], [5, 6, 8], [1], [1]];

				presets[0].put(\amp, [0.5]);
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
			};

		if (secondaryTextTone == "approval") {
				~secondaryRate = [[2, 2, 2, 3, 2, 2, 2, 2, 2, 3, 2, 2, 2, 2, 5], [0, 0, 0, 1.5, 1.5, 1.5, 1.5, 1.5, 1.8], [5, 6, 8], [1], [1]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.5]);
				secondaryPresets[1].put(\filterFreq, 1500);
				secondaryPresets[1].put(\filterRes, 3);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "anger") {
				~rate = [[0.5, 0.2, 0.4], [0], [0.2], [1], [1]];

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
			};

		if (secondaryTextTone == "anger") {
				~secondaryRate = [[0.5, 0.2, 0.4], [0], [0.2], [1], [1]];

				secondaryPresets[0].put(\amp, [0.8]);
				secondaryPresets[0].put(\filterFreq, 3000);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\filterFreq, 3000);
				secondaryPresets[1].put(\filterRes, 4);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "annoyance") {
				~rate = [[1], [0], [5, 6, 8], [1], [1]];

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
			};

		if (secondaryTextTone == "annoyance") {
			~secondaryRate = [[1], [0], [5, 6, 8], [1], [1]];

			secondaryPresets[0].put(\amp, [0.5]);
			secondaryPresets[0].put(\filterFreq, 3000);
			secondaryPresets[0].put(\filterRes, 3);
			secondaryPresets[0].put(\start, 0);
			secondaryPresets[0].put(\end, 1);
			secondaryPresets[0].put(\loop, 1);
			secondaryPresets[0].put(\lfoPseqValue, 0);
			secondaryPresets[0].put(\reverbMix, 7);
			secondaryPresets[0].put(\pitchShift, 0);
			secondaryPresets[0].put(\delayMaxTime, 0);
			secondaryPresets[0].put(\delayTime, 0);
			secondaryPresets[0].put(\decayTime, 0);

			secondaryPresets[1].put(\amp, [0]);
			secondaryPresets[1].put(\filterFreq, 1500);
			secondaryPresets[1].put(\filterRes, 3);
			secondaryPresets[1].put(\start, 0);
			secondaryPresets[1].put(\end, 1);
			secondaryPresets[1].put(\loop, 1);
			secondaryPresets[1].put(\lfoPseqValue, 0);
			secondaryPresets[1].put(\reverbMix, 1);
			secondaryPresets[1].put(\pitchShift, 0);
			secondaryPresets[1].put(\delayMaxTime, 0);
			secondaryPresets[1].put(\delayTime, 0);
			secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "disappointment") {
				~rate = [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 1, 1], [1], [5, 6, 8], [1], [1]];

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
			};

		if (secondaryTextTone == "disappointment") {
				~secondaryRate = [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 1, 1], [1], [5, 6, 8], [1], [1]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 3000);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.5]);
				secondaryPresets[1].put(\filterFreq, 1500);
				secondaryPresets[1].put(\filterRes, 3);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};

		if (textTone == "disapproval") {
				~rate = [[0.5, 0.5, 0.5, 0.5, 0.5, 0.7, 0.7, 0.7, 0.7, 0.5, 0.5, 0.5], [1], [5, 6, 8], [1], [1]];

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
			};

		if (secondaryTextTone == "disapproval") {
				~secondaryRate = [[0.5, 0.5, 0.5, 0.5, 0.5, 0.7, 0.7, 0.7, 0.7, 0.5, 0.5, 0.5], [1], [5, 6, 8], [1], [1]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 3000);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.5]);
				secondaryPresets[1].put(\filterFreq, 3000);
				secondaryPresets[1].put(\filterRes, 4);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};

		if (textTone == "disgust") {
			~rate = [[0.5, 0.2, 0.4], [1], [5, 6, 8], [1], [1]];

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
			};


		if (secondaryTextTone == "disgust") {
			~secondaryRate = [[0.5, 0.2, 0.4], [1], [5, 6, 8], [1], [1]];

				secondaryPresets[0].put(\rate, [0.5, 0.2, 0.4]);
				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 3000);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\rate, [1]);
				secondaryPresets[1].put(\amp, [0.5]);
				secondaryPresets[1].put(\filterFreq, 3000);
				secondaryPresets[1].put(\filterRes, 4);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "sadness") {
				~rate = [[0.5], [6.5, 6.5, 6.5, 6.5], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

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

				presets[1].put(\amp, [0.1]);
				presets[1].put(\filterFreq, 800);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0.05);
				presets[1].put(\reverbMix, 20);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 5);
				presets[1].put(\delayTime, 3);
				presets[1].put(\decayTime, 25);

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
			};


		if (secondaryTextTone == "sadness") {
				~secondaryRate = [[0.5], [6.5, 6.5, 6.5, 6.5], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.1]);
				secondaryPresets[1].put(\filterFreq, 800);
				secondaryPresets[1].put(\filterRes, 3);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0.05);
				secondaryPresets[1].put(\reverbMix, 20);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 5);
				secondaryPresets[1].put(\delayTime, 3);
				secondaryPresets[1].put(\decayTime, 25);
		};

		if (textTone == "grief") {
			~rate = [[1], [0], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

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
			};

		if (secondaryTextTone == "grief") {
			~secondaryRate = [[1], [0], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\modFreq, 0.5);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};


		if (textTone == "caring") {
				~rate = [[1, 1, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 1.5, 1.8, 1.8, 1.8, 1.8, 1.7, 1.7, 1.7, 1.7],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

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

				presets[1].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

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
			};

		if (secondaryTextTone == "caring") {
				~secondaryRate = [[1, 1, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 1.5, 1.8, 1.8, 1.8, 1.8, 1.7, 1.7, 1.7, 1.7],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "nervousness") {
			~rate = [[1],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

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
			};

		if (secondaryTextTone == "nervousness") {
			~secondaryRate = [[1],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\modFreq, 0.5);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};

		if (textTone == "remorse") {
			~rate = [[1],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

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
			};


		if (secondaryTextTone == "remorse") {
			~secondaryRate = [[1],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\modFreq, 0.5);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};

		if (textTone == "embarrassment") {
			~rate = [[1],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

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
			};

		if (secondaryTextTone == "embarrassment") {
			~secondaryRate = [[1],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\modFreq, 0.5);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};


		if (textTone == "fear") {
				~rate = [[1.3, 1.24, 1.24, 1.24, 1.24, 1.24, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8], [0], [3], [0], [3]];

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
			};

		if (secondaryTextTone == "fear") {
				~secondaryRate = [[1.3, 1.24, 1.24, 1.24, 1.24, 1.24, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8], [0], [3], [0], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 3000);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.5]);
				secondaryPresets[1].put(\filterFreq, 1500);
				secondaryPresets[1].put(\filterRes, 3);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "confusion") {
				~rate = [[1, 2, 1.6, 3, 1.1], [1], [3], [0], [3]];

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
			};

		if (secondaryTextTone == "confusion") {
				~secondaryRate = [[1, 2, 1.6, 3, 1.1], [1], [3], [0], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0.1);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\filterFreq, 1500);
				secondaryPresets[1].put(\filterRes, 3);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "relief") {
				~rate = [[2.7, 2.5, 2.9, 2.6], [1], [1], [1], [1]];

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
			};

		if (secondaryTextTone == "relief") {
				~secondaryRate = [[2.7, 2.5, 2.9, 2.6], [1], [1], [1], [1]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 3000);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.5]);
				secondaryPresets[1].put(\filterFreq, 1500);
				secondaryPresets[1].put(\filterRes, 3);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "realization") {
				~rate = [[3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 4.5], [1.5, 2.5, 2.5, 2.5, 2.5, 3.5], [5, 6, 8], [1], [1]];


				presets[0].put(\amp, [0.3]);
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

				presets[1].put(\amp, [0.3]);
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
			};

		if (secondaryTextTone == "realization") {
				~secondaryRate = [[3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 4.5], [1.5, 2.5, 2.5, 2.5, 2.5, 3.5], [5, 6, 8], [1], [1]];


				secondaryPresets[0].put(\amp, [0.3]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.3]);
				secondaryPresets[1].put(\filterFreq, 1500);
				secondaryPresets[1].put(\filterRes, 3);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};


		if (textTone == "pride") {
			~secondaryRate = [[1], [1.5, 2.5, 2.5, 2.5, 2.5, 3.5], [5, 6, 8], [1], [1]];

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
			};

		if (secondaryTextTone == "pride") {
			~secondaryRate = [[1], [1.5, 2.5, 2.5, 2.5, 2.5, 3.5], [5, 6, 8], [1], [1]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};

		if (textTone == "curiosity") {
				~rate = [[2.5, 2.5, 2.5, 3, 3, 2.5, 3.8, 3.8, 2.5], [2, 2, 2, 2, 2, 2, 3, 3, 3], [5, 6, 8], [1], [1]];

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

				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 3000);
				presets[1].put(\filterRes, 1);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 10);
				presets[1].put(\delayTime, 5);
				presets[1].put(\decayTime, 5);

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
			};


		if (secondaryTextTone == "curiosity") {
				~secondaryRate = [[2.5, 2.5, 2.5, 3, 3, 2.5, 3.8, 3.8, 2.5], [2, 2, 2, 2, 2, 2, 3, 3, 3], [5, 6, 8], [1], [1]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.5]);
				secondaryPresets[1].put(\filterFreq, 3000);
				secondaryPresets[1].put(\filterRes, 1);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 10);
				secondaryPresets[1].put(\delayTime, 5);
				secondaryPresets[1].put(\decayTime, 5);
		};

		if (textTone == "neutral") {
				~rate = [[2.5], [1], [3], [0], [3]];

				presets[0].put(\amp, [0.3]);
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
			};


		if (secondaryTextTone == "neutral") {
				~secondaryRate = [[2.5], [1], [3], [0], [3]];

				secondaryPresets[0].put(\amp, [0.3]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0.1]);
				secondaryPresets[1].put(\filterFreq, 1500);
				secondaryPresets[1].put(\filterRes, 3);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};

		if (textTone == "optimism") {
			~rate = [[1], [1], [3], [0], [3]];

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
			};

		if (secondaryTextTone == "optimism") {
			~secondaryRate = [[1], [1], [3], [0], [3]];

				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);

		};

		if (textTone == "surprise") {
				~rate = [[3.3, 3.7, 3.1, 3.9], [0], [2.9], [0], [3]];

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
			};

		if (secondaryTextTone == "surprise") {
				~secondaryRate = [[3.3, 3.7, 3.1, 3.9], [0], [2.9], [0], [3]];

				secondaryPresets[0].put(\amp, [0.5, 0.4, 0.2]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0.1);
				secondaryPresets[0].put(\reverbMix, 7);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\modFreq, 0.5);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		if (textTone == "") {
			~rate = [[1], [0], [2.9], [0], [3]];

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
			};

		if (secondaryTextTone == "") {
			~secondaryRate = [[1], [0], [2.9], [0], [3]];

				secondaryPresets[0].put(\rate, [1]);
				secondaryPresets[0].put(\amp, [0.5]);
				secondaryPresets[0].put(\filterFreq, 1500);
				secondaryPresets[0].put(\filterRes, 3);
				secondaryPresets[0].put(\start, 0);
				secondaryPresets[0].put(\end, 1);
				secondaryPresets[0].put(\loop, 1);
				secondaryPresets[0].put(\lfoPseqValue, 0);
				secondaryPresets[0].put(\reverbMix, 1);
				secondaryPresets[0].put(\pitchShift, 0);
				secondaryPresets[0].put(\delayMaxTime, 0);
				secondaryPresets[0].put(\delayTime, 0);
				secondaryPresets[0].put(\decayTime, 0);

				secondaryPresets[1].put(\rate, [0]);
				secondaryPresets[1].put(\amp, [0]);
				secondaryPresets[1].put(\start, 0);
				secondaryPresets[1].put(\end, 1);
				secondaryPresets[1].put(\loop, 1);
				secondaryPresets[1].put(\lfoPseqValue, 0);
				secondaryPresets[1].put(\reverbMix, 1);
				secondaryPresets[1].put(\pitchShift, 0);
				secondaryPresets[1].put(\delayMaxTime, 0);
				secondaryPresets[1].put(\delayTime, 0);
				secondaryPresets[1].put(\decayTime, 0);
		};

		allPresets = [presets, secondaryPresets];

		^allPresets;
	}
}
