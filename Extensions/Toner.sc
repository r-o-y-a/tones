Toner {
	var synthdef, synthdef2, offline = false;
	var presets, secondaryPresets, allPresets, patterns;
	var activeGroup = 1;

	*new {
		arg synthDef, synthDef2, offline;
		~group1 = Group.new;
		~group2 = Group.new;
		^super.newCopyArgs(synthDef, synthDef2, offline);
	}

	t { | text, hideSecondary, runOffline |
		var allPresets, patterns, playSecondary, tones, textTones;
		var primaryTextTone, secondaryTextTone;

		if (offline == true) {
			runOffline = 1;
		};

		textTones = this.getTonesFromText(text, hideSecondary, runOffline);
		tones = textTones.split($,);

		primaryTextTone = tones[0];
		secondaryTextTone = tones[1];

		allPresets = this.getPresets(primaryTextTone, secondaryTextTone);

		if (allPresets != nil) {
			patterns = this.playSamples(allPresets, synthdef, synthdef2, playSecondary);

			^patterns;
		}
	}

	loadPreprocessor {
		thisProcess.interpreter.preProcessor = {|codeBlock|
			var c, d;
			c = codeBlock.split($ );
			d = codeBlock.replace(":: ", replace:"");
			d = d.replace(": ", replace:"");


			if(codeBlock.beginsWith(": "), {
				"p = t.t(\""++d++"\", 1);"
			}, {
				if(codeBlock.beginsWith(":: "), {
					"p = t.t(\""++d++"\");"
				}, {
					codeBlock
				});
			});
		};
	}

	/* -- private methods -- */

	playSamples { | allPresets, synthdef, synthdef2, playSecondary |

		if (~currentSynths.notNil) {

			var newSynths, newGroup;
			var endTime = 1;
			var steps = 150;
			var stepTime = endTime / steps;

			var fadeInSteps = 100;
			var increment = 1 / fadeInSteps;
			var currentFade = 0;

			var fadeGroup;


			fadeGroup = activeGroup;


			if (activeGroup == 1) {
				activeGroup = 2;
			} {
				activeGroup = 1;
			};

			newSynths = this.createSynths(allPresets, synthdef, synthdef2, playSecondary);
			~currentSynths = newSynths;

			// decrease volume of current group
			{

				steps.do { |i|
					var currentAmp = 1.0 - (i / steps);

					if (fadeGroup == 1) {
						~group1.set(\fade, currentAmp);
					} {
						~group2.set(\fade, currentAmp);
					};

					stepTime.wait;
				};

			}.fork();

		} {
			// if no currently playing synths, create first ones
			~currentSynths = this.createSynths(allPresets, synthdef, synthdef2, playSecondary);
		};

		^~currentSynths;
	}


	createSynths { | allPresets, synthdef, synthdef2, playSecondary |
		var p1, p2, p3, p4, p5, p6, p7;
		//var p2nd1, p2nd2;
		var patterns = Dictionary.new;
		var primaryPresets = allPresets[0];
		var secondaryPresets = allPresets[1];
		var pmonoGroup;
		var pdefName1, pdefName2, pdefName3, pdefName4, pdefName5, pdefName6, pdefName7;


		if (activeGroup == 1) {
			pmonoGroup = ~group1;
			pdefName1 = \pSampleA1;
			pdefName2 = \pSampleA2;
			pdefName3 = \pSampleA3;
			pdefName4 = \pSampleA4;
			pdefName5 = \pSampleA5;
			pdefName6 = \pSampleA6;
			pdefName7 = \pSampleA7;
		} {
			pmonoGroup = ~group2;
			pdefName1 = \pSampleB1;
			pdefName2 = \pSampleB2;
			pdefName3 = \pSampleB3;
			pdefName4 = \pSampleB4;
			pdefName5 = \pSampleB5;
			pdefName6 = \pSampleB6;
			pdefName7 = \pSampleB7;
		};

		p1 = Pdef(pdefName1,
			Pmono(
				synthdef,
				\group, pmonoGroup,
				\bufnum, ~buffers[0].bufnum,
				\rate, Pseq(~rate[0], inf),
				\start, primaryPresets[0].at(\start),
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


		p2 = Pdef(pdefName2,
			Pmono(
				synthdef,
				\group, pmonoGroup,
				\bufnum, ~buffers[1].bufnum,
				\rate, Pseq(~rate[1], inf),
				\start, primaryPresets[1].at(\start),
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

		p3 = Pdef(pdefName3,
			Pmono(
				synthdef,
				\group, pmonoGroup,
				\bufnum, ~buffers[2].bufnum,
				\rate, Pseq(~rate[2], inf),
				\start, primaryPresets[2].at(\start),
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


		p4 = Pdef(pdefName4,
			Pmono(
				synthdef,
				\group, pmonoGroup,
				\bufnum, ~buffers[3].bufnum,
				\rate, Pseq(~rate[3], inf),
				\start, primaryPresets[3].at(\start),
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


		p5 = Pdef(pdefName5,
			Pmono(
				synthdef,
				\group, pmonoGroup,
				\bufnum, ~buffers[4].bufnum,
				\rate, Pseq(~rate[4], inf),
				\start, primaryPresets[4].at(\start),
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


		p6 = Pdef(pdefName6,
			Pmono(
				synthdef,
				\group, pmonoGroup,
				\bufnum, ~buffers[5].bufnum,
				\rate, Pseq(~rate[5], inf),
				\start, primaryPresets[5].at(\start),
				\lfoFreq, Pseq([ Pfunc { primaryPresets[5].at(\lfoPseqValue) }], inf),
				\amp, Pseq(primaryPresets[5].at(\amp), inf),
				\filterFreq, primaryPresets[5].at(\filterFreq),
				\filterRes, primaryPresets[5].at(\filterRes),
				\loop, primaryPresets[5].at(\loop),
				\reverbMix, primaryPresets[5].at(\reverbMix),
				\pitchShift, primaryPresets[5].at(\pitchShift),
				\delayMaxTime, primaryPresets[5].at(\delayMaxTime),
				\delayTime, primaryPresets[5].at(\delayTime),
				\pitchShift, primaryPresets[5].at(\pitchShift)
			)
		);
		patterns.put(\p6, p6);
		p6.play;


		p7 = Pdef(pdefName7,
			Pmono(
				synthdef,
				\group, pmonoGroup,
				\bufnum, ~buffers[6].bufnum,
				\rate, Pseq(~rate[6], inf),
				\start, primaryPresets[6].at(\start),
				\lfoFreq, Pseq([ Pfunc { primaryPresets[6].at(\lfoPseqValue) }], inf),
				\amp, Pseq(primaryPresets[6].at(\amp), inf),
				\filterFreq, primaryPresets[6].at(\filterFreq),
				\filterRes, primaryPresets[6].at(\filterRes),
				\loop, primaryPresets[6].at(\loop),
				\reverbMix, primaryPresets[6].at(\reverbMix),
				\pitchShift, primaryPresets[6].at(\pitchShift),
				\delayMaxTime, primaryPresets[6].at(\delayMaxTime),
				\delayTime, primaryPresets[6].at(\delayTime),
				\pitchShift, primaryPresets[6].at(\pitchShift)
			)
		);
		patterns.put(\p7, p7);
		p7.play;


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

	getTonesFromText { | text, hideSecondary, runOffline |
		var textTones, b;
		var textTone, secondaryTextTone;

		"retrieving tone....".postln;

		if (runOffline == false || runOffline.isNil) {
			textTones = this.getTonesFromApi(text);
		};

		if (runOffline == true || textTones.isNil) {
			"ooo".postln;
			textTones = this.getTonesFromStatic(text);
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
		presets = [Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new];
		secondaryPresets = [Dictionary.new, Dictionary.new];


		// todo: return secondaryPresets[0], and secondaryPresets[1] for all presets


		if (textTone == "admiration") {
			~rate = [[0], [6], [0], [0], [7], [0,0,5,5.5,0,0,0,0,0,0], [3]];

			presets[0].put(\amp, [0]);
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

			presets[1].put(\amp, [0.08]);
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

			presets[4].put(\amp, [0.04]);
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

			presets[5].put(\amp, [0.03]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.02]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0], [0], [0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,4,4,4,6,6], [0], [3], [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0], [3,3,3,3,3,3,3,3,3,6,3,3,3,6.5,3,3,3,3,3,3,3,3]];

			presets[0].put(\amp, [0.1]);
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

			presets[2].put(\amp, [0.1]);
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

			presets[5].put(\amp, [0.01]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.01,0.01,0.005,0.004,0.003,0.002,0.001,0.004,0.004,0.005]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1, 2, 1.6, 3, 1.1], [4], [3], [0], [0], [1,0,0,0,0,2,0,0,0,0], [0,0,0,1]];

			presets[0].put(\amp, [0.3]);
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

			presets[1].put(\amp, [0.3]);
			presets[1].put(\start, 0);
			presets[1].put(\end, 1);
			presets[1].put(\loop, 1);
			presets[1].put(\lfoPseqValue, 0);
			presets[1].put(\reverbMix, 1);
			presets[1].put(\pitchShift, 0);
			presets[1].put(\delayMaxTime, 0);
			presets[1].put(\delayTime, 0);
			presets[1].put(\decayTime, 0);

			presets[2].put(\amp, [0.2]);
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


			presets[5].put(\amp, [0.1]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.1]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[20], [2], [2.4,2.4,2.4,2.4,2.3,2.3,2.7,2,2,2,2,2,2,2,2,2,2,2], [1], [0], [0], [1]];

			presets[0].put(\amp, [0.003]);
			presets[0].put(\filterFreq, 500);
			presets[0].put(\filterRes, 1);
			presets[0].put(\start, 0);
			presets[0].put(\end, 1);
			presets[0].put(\loop, 1);
			presets[0].put(\lfoPseqValue, 0);
			presets[0].put(\reverbMix, 4);
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

			presets[2].put(\amp, [0.3]);
			presets[2].put(\filterFreq, 500);
			presets[2].put(\filterRes, 3);
			presets[2].put(\start, 0);
			presets[2].put(\loop, 1);
			presets[2].put(\lfoPseqValue, 0);
			presets[2].put(\reverbMix, 4);
			presets[2].put(\pitchShift, 0);
			presets[2].put(\delayMaxTime, 0.1);
			presets[2].put(\delayTime, 0.1);
			presets[2].put(\decayTime, 4);

			presets[3].put(\amp, [0.05]);
			presets[3].put(\start, 0);
			presets[3].put(\loop, 1);
			presets[3].put(\filterFreq, 500);
			presets[3].put(\filterRes, 3);
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
			presets[4].put(\loop, 1);
			presets[4].put(\lfoPseqValue, 0);
			presets[4].put(\reverbMix, 4);
			presets[4].put(\pitchShift, 0);
			presets[4].put(\delayMaxTime, 0.1);
			presets[4].put(\delayTime, 0.1);
			presets[4].put(\decayTime, 4);

			presets[5].put(\amp, [0]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.04]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[0.5, 0.7, 0.6, 0.5,0.5,0.5], [0.6, 0.6, 0.6, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5], [4, 4.1, 4.1, 4, 4, 4.1, 4, 4, 4], [0], [0.5, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0,0], [0,0,0,0,0,0,0,0,0,0,0,0.5, 0.4], [0.3]];

			presets[0].put(\amp, [0.1]);
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

			presets[1].put(\amp, [0.4]);
			presets[1].put(\filterFreq, 3000);
			presets[1].put(\filterRes, 2);
			presets[1].put(\start, 0);
			presets[1].put(\end, 1);
			presets[1].put(\loop, 1);
			presets[1].put(\lfoPseqValue, 0);
			presets[1].put(\reverbMix, 1);
			presets[1].put(\pitchShift, 0);
			presets[1].put(\delayMaxTime, 0);
			presets[1].put(\delayTime, 0);
			presets[1].put(\decayTime, 0);

			presets[2].put(\amp, [0.02]);
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

			presets[3].put(\amp, [0]);
			presets[3].put(\filterFreq, 3000);
			presets[3].put(\filterRes, 4);
			presets[3].put(\start, 0);
			presets[3].put(\end, 1);
			presets[3].put(\loop, 1);
			presets[3].put(\lfoPseqValue, 0);
			presets[3].put(\reverbMix, 1);
			presets[3].put(\pitchShift, 0);
			presets[3].put(\delayMaxTime, 10);
			presets[3].put(\delayTime, 10);
			presets[3].put(\decayTime, 10);

			presets[4].put(\amp, [0.1]);
			presets[4].put(\filterFreq, 3000);
			presets[4].put(\filterRes, 10);
			presets[4].put(\start, 0);
			presets[4].put(\end, 1);
			presets[4].put(\loop, 1);
			presets[4].put(\lfoPseqValue, 0);
			presets[4].put(\reverbMix, 4);
			presets[4].put(\pitchShift, 0);
			presets[4].put(\delayMaxTime, 0);
			presets[4].put(\delayTime, 0);
			presets[4].put(\decayTime, 0);

			presets[5].put(\amp, [0.1]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 1500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0.01);
			presets[6].put(\reverbMix, 0);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0);
			presets[6].put(\delayTime, 0);
			presets[6].put(\decayTime, 0);
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
			~rate = [[0.5], [4], [1], [0], [0,0,0,0,0,0,0,0,1.5,1.5,1.5,1.5,0,0,0,0,0,0,0,1.5,0,0.5,0], [0.2, 0.1], [0.1]];

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

			presets[1].put(\amp, [0.04]);
			presets[1].put(\filterFreq, 1500);
			presets[1].put(\filterRes, 3);
			presets[1].put(\start, 0);
			presets[1].put(\end, 1);
			presets[1].put(\loop, 1);
			presets[1].put(\lfoPseqValue, 0.1);
			presets[1].put(\reverbMix, 1);
			presets[1].put(\pitchShift, 0);
			presets[1].put(\delayMaxTime, 0);
			presets[1].put(\delayTime, 0);
			presets[1].put(\decayTime, 0);

			presets[2].put(\amp, [0.3]);
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

			presets[3].put(\amp, [0]);
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

			presets[4].put(\amp, [0.1]);
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

			presets[5].put(\amp, [0.6]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[0.5], [1], [0], [0], [0], [0], [0]];

			presets[0].put(\amp, [0.6]);
			presets[0].put(\filterFreq, 1);
			presets[0].put(\filterRes, 1);
			presets[0].put(\start, 0);
			presets[0].put(\loop, 1);
			presets[0].put(\lfoPseqValue, 0);
			presets[0].put(\reverbMix, 0);
			presets[0].put(\pitchShift, 0);
			presets[0].put(\delayMaxTime, 0);
			presets[0].put(\delayTime, 0);
			presets[0].put(\decayTime, 0);

			presets[1].put(\amp, [0.17]);
			presets[1].put(\filterFreq, 3000);
			presets[1].put(\filterRes, 3);
			presets[1].put(\start, 0);
			presets[1].put(\loop, 1);
			presets[1].put(\lfoPseqValue, 0);
			presets[1].put(\reverbMix, 0.4);
			presets[1].put(\pitchShift, 0);
			presets[1].put(\delayMaxTime, 0);
			presets[1].put(\delayTime, 0);
			presets[1].put(\decayTime, 0);

			presets[2].put(\amp, [0]);
			presets[2].put(\filterFreq, 500);
			presets[2].put(\filterRes, 3);
			presets[2].put(\start, 0);
			presets[2].put(\loop, 1);
			presets[2].put(\lfoPseqValue, 0);
			presets[2].put(\reverbMix, 0.5);
			presets[2].put(\pitchShift, 0);
			presets[2].put(\delayMaxTime, 0.1);
			presets[2].put(\delayTime, 0.1);
			presets[2].put(\decayTime, 4);

			presets[3].put(\amp, [0]);
			presets[3].put(\filterFreq, 500);
			presets[3].put(\filterRes, 3);
			presets[3].put(\start, 0);
			presets[3].put(\loop, 1);
			presets[3].put(\lfoPseqValue, 0);
			presets[3].put(\reverbMix, 0.5);
			presets[3].put(\pitchShift, 0);
			presets[3].put(\delayMaxTime, 0.1);
			presets[3].put(\delayTime, 0.1);
			presets[3].put(\decayTime, 4);

			presets[4].put(\amp, [0]);
			presets[4].put(\filterFreq, 1500);
			presets[4].put(\filterRes, 3);
			presets[4].put(\start, 0);
			presets[4].put(\loop, 1);
			presets[4].put(\lfoPseqValue, 0);
			presets[4].put(\reverbMix, 0.5);
			presets[4].put(\pitchShift, 0);
			presets[4].put(\delayMaxTime, 0);
			presets[4].put(\delayTime, 0);
			presets[4].put(\decayTime, 0);


			presets[5].put(\amp, [0]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[0.5], [0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4,0.4,0.4,0.4], [0.2], [0.5], [1], [0,0,0,0, 1.5, 0, 0, 1.48, 1.40,1.42,0, 0,0,0,0,0,0,0,0,0,0], [0,0,0,0.5, 0, 0, 0.48, 0.40,0.42,0, 0, 0,0,0,0,0,0,0,0,0,0,0]];

			presets[0].put(\amp, [0.5]);
			presets[0].put(\filterFreq, 3000);
			presets[0].put(\filterRes, 3);
			presets[0].put(\start, 0);
			presets[0].put(\loop, 1);
			presets[0].put(\lfoPseqValue, 0);
			presets[0].put(\reverbMix, 7);
			presets[0].put(\pitchShift, 0);
			presets[0].put(\delayMaxTime, 0);
			presets[0].put(\delayTime, 0);
			presets[0].put(\decayTime, 0);

			presets[1].put(\amp, [0.8]);
			presets[1].put(\filterFreq, 3000);
			presets[1].put(\filterRes, 4);
			presets[1].put(\start, 0);
			presets[1].put(\loop, 1);
			presets[1].put(\lfoPseqValue, 0);
			presets[1].put(\reverbMix, 0.4);
			presets[1].put(\pitchShift, 0);
			presets[1].put(\delayMaxTime, 0);
			presets[1].put(\delayTime, 0);
			presets[1].put(\decayTime, 0);

			presets[2].put(\amp, [0]);
			presets[2].put(\filterFreq, 500);
			presets[2].put(\filterRes, 3);
			presets[2].put(\start, 0);
			presets[2].put(\loop, 1);
			presets[2].put(\lfoPseqValue, 0);
			presets[2].put(\reverbMix, 1);
			presets[2].put(\pitchShift, 0);
			presets[2].put(\delayMaxTime, 0.1);
			presets[2].put(\delayTime, 0.1);
			presets[2].put(\decayTime, 6);

			presets[3].put(\amp, [0.7]);
			presets[3].put(\filterFreq, 500);
			presets[3].put(\filterRes, 3);
			presets[3].put(\start, 0);
			presets[3].put(\loop, 1);
			presets[3].put(\lfoPseqValue, 0);
			presets[3].put(\reverbMix, 1);
			presets[3].put(\pitchShift, 0);
			presets[3].put(\delayMaxTime, 0.1);
			presets[3].put(\delayTime, 0.1);
			presets[3].put(\decayTime, 4);

			presets[4].put(\amp, [0]);
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

			presets[5].put(\amp, [0.01]);
			presets[5].put(\filterFreq, 1500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.3]);
			presets[6].put(\filterFreq, 1500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[0.5, 0.2, 0.4], [1], [5, 6, 8], [1], [1], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[0.5], [6.5, 6.5, 6.5, 6.5], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3], [1], [1]];

			presets[0].put(\amp, [0.1]);
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
			presets[1].put(\filterFreq, 500);
			presets[1].put(\filterRes, 3);
			presets[1].put(\start, 0);
			presets[1].put(\end, 1);
			presets[1].put(\loop, 1);
			presets[1].put(\lfoPseqValue, 0);
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

			presets[5].put(\amp, [0]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1], [0], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1, 1, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 1.5, 1.8, 1.8, 1.8, 1.8, 1.7, 1.7, 1.7, 1.7],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1.3, 1.24, 1.24, 1.24, 1.24, 1.24, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8], [0], [3], [0], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1, 2, 1.6, 3, 1.1], [1], [3], [0], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[2.7, 2.5, 2.9, 2.6], [1], [1], [1], [1], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[0], [1,1,1,1,1,1,1.8, 1.7,2,1,1,1,1,1], [1,1,1,1,1,1,1.2,1,1,1,1,1,1,1], [0], [5], [0], [0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0]];

			presets[0].put(\amp, [0]);
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

			presets[2].put(\amp, [0.4]);
			presets[2].put(\filterFreq, 500);
			presets[2].put(\filterRes, 3);
			presets[2].put(\start, 0);
			presets[2].put(\loop, 1);
			presets[2].put(\lfoPseqValue, 0);
			presets[2].put(\reverbMix, 4);
			presets[2].put(\pitchShift, 0);
			presets[2].put(\delayMaxTime, 0.1);
			presets[2].put(\delayTime, 0.1);
			presets[2].put(\decayTime, 4);

			presets[3].put(\amp, [0.05]);
			presets[3].put(\start, 0);
			presets[3].put(\loop, 1);
			presets[3].put(\filterFreq, 500);
			presets[3].put(\filterRes, 3);
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
			presets[4].put(\loop, 1);
			presets[4].put(\lfoPseqValue, 0);
			presets[4].put(\reverbMix, 4);
			presets[4].put(\pitchShift, 0);
			presets[4].put(\delayMaxTime, 0.1);
			presets[4].put(\delayTime, 0.1);
			presets[4].put(\decayTime, 4);

			presets[5].put(\amp, [0]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.05]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1,2.5, 2.5, 2.5, 3, 3, 2.5, 3.8, 3.8, 2.5, 1, 1, 1, 1, 1, 1 ,1, 0.5, 0.5, 0.5,0.5,0.5,0.5,0.5,0.5], [0,0,0,8.5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0], [0,0,0,0,0,0,0,3, 8, 3,0,0,0,0,0,0,0,0,0,0,0,0], [1], [0], [0], [0.5]];

			presets[0].put(\amp, [0.13]);
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

			presets[1].put(\amp, [0.02]);
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

			presets[2].put(\amp, [0.004]);
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

			presets[3].put(\amp, [0.6]);
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


			presets[5].put(\amp, [0]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1,1,2,1,1,1,1,1,1,1], [0], [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2], [0], [0], [0,0,0,0,0,0,0,0,0,0,0, 0, 0, 0, 0, 0, 0, 0, 2.5, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0,0, 0, 0, 0, 0], [0,0,0,0,0,0,0, 0, 0, 0, 1, 0, 0, 0, 0, 0]];

			presets[0].put(\amp, [0.2]);
			presets[0].put(\filterFreq, 3000);
			presets[0].put(\filterRes, 3);
			presets[0].put(\start, 1000000); //1000000
			presets[0].put(\loop, 1);
			presets[0].put(\lfoPseqValue, [0]);
			presets[0].put(\reverbMix, 0);
			presets[0].put(\pitchShift, 0);
			presets[0].put(\delayMaxTime, 0);
			presets[0].put(\delayTime, 0);
			presets[0].put(\decayTime, 0);

			presets[1].put(\amp, [0.3]);
			presets[1].put(\filterFreq, 3000);
			presets[1].put(\filterRes, 3);
			presets[1].put(\start, 0);
			presets[1].put(\loop, 1);
			presets[1].put(\lfoPseqValue, 0);
			presets[1].put(\reverbMix, 0.4);
			presets[1].put(\pitchShift, 0);
			presets[1].put(\delayMaxTime, 0);
			presets[1].put(\delayTime, 0);
			presets[1].put(\decayTime, 0);

			presets[2].put(\amp, [0.3]);
			presets[2].put(\filterFreq, 500);
			presets[2].put(\filterRes, 3);
			presets[2].put(\start, 0);
			presets[2].put(\loop, 0);
			presets[2].put(\lfoPseqValue, 0);
			presets[2].put(\reverbMix, 4);
			presets[2].put(\pitchShift, 0);
			presets[2].put(\delayMaxTime, 0.1);
			presets[2].put(\delayTime, 0.1);
			presets[2].put(\decayTime, 4);

			presets[3].put(\amp, [0.15]);
			presets[3].put(\filterFreq, 3000);
			presets[3].put(\filterRes, 3);
			presets[3].put(\start, 0);
			presets[3].put(\loop, 1);
			presets[3].put(\lfoPseqValue, 0);
			presets[3].put(\reverbMix, 1);
			presets[3].put(\pitchShift, 0);
			presets[3].put(\delayMaxTime, 0);
			presets[3].put(\delayTime, 0);
			presets[3].put(\decayTime, 0);

			presets[4].put(\amp, [0]);
			presets[4].put(\filterFreq, 3000);
			presets[4].put(\filterRes, 3);
			presets[4].put(\start, 0);
			presets[4].put(\loop, 1);
			presets[4].put(\lfoPseqValue, 0);
			presets[4].put(\reverbMix, 4);
			presets[4].put(\pitchShift, 0);
			presets[4].put(\delayMaxTime, 0.1);
			presets[4].put(\delayTime, 0.1);
			presets[4].put(\decayTime, 4);

			presets[5].put(\amp, [0.08]);
			presets[5].put(\filterFreq, 1500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 3);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 3);

			presets[6].put(\amp, [0.08]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 3);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 3);
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
			~rate = [[1], [1], [3], [0], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[3.3, 3.7, 3.1, 3.9], [0], [2.9], [0], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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
			~rate = [[1], [0], [2.9], [0], [3], [1], [1]];

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


			presets[5].put(\amp, [0.5]);
			presets[5].put(\filterFreq, 500);
			presets[5].put(\filterRes, 3);
			presets[5].put(\start, 0);
			presets[5].put(\end, 1);
			presets[5].put(\loop, 1);
			presets[5].put(\lfoPseqValue, 0);
			presets[5].put(\reverbMix, 4);
			presets[5].put(\pitchShift, 0);
			presets[5].put(\delayMaxTime, 0.1);
			presets[5].put(\delayTime, 0.1);
			presets[5].put(\decayTime, 4);

			presets[6].put(\amp, [0.5]);
			presets[6].put(\filterFreq, 500);
			presets[6].put(\filterRes, 3);
			presets[6].put(\start, 0);
			presets[6].put(\end, 1);
			presets[6].put(\loop, 1);
			presets[6].put(\lfoPseqValue, 0);
			presets[6].put(\reverbMix, 4);
			presets[6].put(\pitchShift, 0);
			presets[6].put(\delayMaxTime, 0.1);
			presets[6].put(\delayTime, 0.1);
			presets[6].put(\decayTime, 4);
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

	getTonesFromStatic { | text, hideSecondary, runOffline |
		var textTones;

		switch(text,
			"America I have given you all and now I am nothing.", {
				textTones = "disappointment, gratitude";
			},
			"America two dollars and twentyseven cents January 17, 1956.", {
				textTones = "neutral, approval";
			},
			"I can’t stand my own mind.", {
				textTones = "disapproval, anger";
			},
			"I can't stand my own mind.", {
				textTones = "disapproval, anger";
			},
			"America when will we end the human war?", {
				textTones = "curiosity, confusion";
			},
			"Go fuck yourself with your atom bomb.", {
				textTones = "anger, neutral";
			},
			"I don’t feel good don’t bother me.", {
				textTones = "annoyance, optimism";
			},
			"I don't feel good don't bother me.", {
				textTones = "annoyance, optimism";
			},
			"I won’t write my poem till I’m in my right mind.", {
				textTones = "realization, neutral";
			},
			"I won't write my poem till I'm in my right mind.", {
				textTones = "realization, neutral";
			},
			"America when will you be angelic?", {
				textTones = "neutral, realization";
			},
			"When will you take off your clothes?", {
				textTones = "neutral, curiosity";
			},
			"When will you look at yourself through the grave?", {
				textTones = "curiosity, disapproval";
			},
			"When will you be worthy of your million Trotskyites?", {
				textTones = "curiosity, confusion";
			},
			"America why are your libraries full of tears?", {
				textTones = "neutral, sadness";
			},
			"America when will you send your eggs to India?", {
				textTones = "curiosity, neutral";
			},
			"I’m sick of your insane demands.", {
				textTones = "annoyance, anger";
			},
			"I'm sick of your insane demands.", {
				textTones = "annoyance, anger";
			},
			"When can I go into the supermarket and buy what I need with my good looks?", {
				textTones = "curiosity, neutral";
			},
			"America after all it is you and I who are perfect not the next world.", {
				textTones = "neutral, annoyance";
			},
			"Your machinery is too much for me.", {
				textTones = "love, admiration";
			},
			"You made me want to be a saint.", {
				textTones = "admiration, desire";
			},
			"There must be some other way to settle this argument.", {
				textTones = "confusion, disapproval";
			},
			"Burroughs is in Tangiers I don’t think he’ll come back it’s sinister.", {
				textTones = "disapproval, neutral";
			},
			"Burroughs is in Tangiers I don't think he'll come back it's sinister.", {
				textTones = "disapproval, neutral";
			},
			"Are you being sinister or is this some form of practical joke?", {
				textTones = "curiosity, confusion";
			},
			"I’m trying to come to the point.", {
				textTones = "desire, neutral";
			},
			"I'm trying to come to the point.", {
				textTones = "desire, neutral";
			},
			"I refuse to give up my obsession.", {
				textTones = "disapproval, approval";
			},
			"America stop pushing I know what I’m doing.", {
				textTones = "annoyance, neutral";
			},
			"America stop pushing I know what I'm doing.", {
				textTones = "annoyance, neutral";
			},
			"America the plum blossoms are falling.", {
				textTones = "realization, approval";
			},
			"I haven’t read the newspapers for months, everyday somebody goes on trial for murder.", {
				textTones = "disapproval, neutral";
			},
			"I haven't read the newspapers for months, everyday somebody goes on trial for murder.", {
				textTones = "disapproval, neutral";
			},
			"America I feel sentimental about the Wobblies.", {
				textTones = "sadness, grief";
			},
			"America I used to be a communist when I was a kid I’m not sorry.", {
				textTones = "neutral, realization";
			},
			"America I used to be a communist when I was a kid I'm not sorry.", {
				textTones = "neutral, realization";
			},
			"I smoke marijuana every chance I get.", {
				textTones = "approval, neutral";
			},
			"I sit in my house for days on end and stare at the roses in the closet.", {
				textTones = "admiration, realization";
			},
			"When I go to Chinatown I get drunk and never get laid.", {
				textTones = "neutral, realization";
			},
			"My mind is made up there’s going to be trouble.", {
				textTones = "fear, nervousness";
			},
			"My mind is made up there's going to be trouble.", {
				textTones = "fear, nervousness";
			},
			"You should have seen me reading Marx.", {
				textTones = "neutral";
			},
			"My psychoanalyst thinks I’m perfectly right.", {
				textTones = "neutral, approval";
			},
			"My psychoanalyst thinks I'm perfectly right.", {
				textTones = "neutral, approval";
			},
			"I won’t say the Lord’s Prayer.", {
				textTones = "disapproval, neutral";
			},
			"I won't say the Lord's Prayer.", {
				textTones = "disapproval, neutral";
			},
			"I have mystical visions and cosmic vibrations.", {
				textTones = "neutral, surprise,";
			},
			"America I still haven’t told you what you did to Uncle Max after he came over from Russia.", {
				textTones = "neutral";
			},
			"America I still haven't told you what you did to Uncle Max after he came over from Russia.", {
				textTones = "neutral";
			},
			"I’m addressing you.", {
				textTones = "neutral";
			},
			"I'm addressing you.", {
				textTones = "neutral";
			},
			"Are you going to let your emotional life be run by Time Magazine?", {
				textTones = "caring, curiosity";
			},
			"I’m obsessed by Time Magazine.", {
				textTones = "desire, approval";
			},
			"I'm obsessed by Time Magazine.", {
				textTones = "desire, approval";
			},
			"I read it every week.", {
				textTones = "neutral, approval";
			},
			"Its cover stares at me every time I slink past the corner candystore.", {
				textTones = "neutral, annoyance";
			},
			"I read it in the basement of the Berkeley Public Library.", {
				textTones = "neutral, approval";
			},
			"It’s always telling me about responsibility. Businessmen are serious. Movie producers are serious. Everybody’s serious but me.", {
				textTones = "neutral, annoyance";
			},
			"It's always telling me about responsibility. Businessmen are serious. Movie producers are serious. Everybody's serious but me.", {
				textTones = "neutral, annoyance";
			},
			"It occurs to me that I am America.", {
				textTones = "realization, neutral";
			},
			"I am talking to myself again.", {
				textTones = "neutral, neutral";
			},
			"neutral", {
				textTones = "neutral, neutral";
			},
			{
				textTones = "neutral, neutral";
			},
		);

		^textTones;
	}
}
