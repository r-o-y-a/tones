(
s.waitForBoot {

	~rate = [];

	a = PathName(thisProcess.nowExecutingPath).pathOnly ++ "samples/";
	~buffers = PathName(a).entries.collect{|path| Buffer.read(s, path.fullPath)};

	SynthDef(\samplePlayer1, { | bufnum = 0, rate = 1, amp = 0, lfoFreq = 1, filterFreq = 1000, filterRes = 0.5, loop = 1, reverbMix = 0.5, pitchShift = 1, delayMaxTime = 0, delayTime = 0, decayTime = 0, fade = 1, start = 0 |

		var lfo = SinOsc.kr(lfoFreq).range(0.8, 1.2);
		var playbackRate = rate * lfo;

		var sound = PlayBuf.ar(2, bufnum, playbackRate, loop: loop, startPos: start);
		sound = sound *  amp;

		// delay
		sound = CombL.ar(sound, maxdelaytime: delayMaxTime, delaytime: delayTime, decaytime: decayTime);

		//sound = LPF.ar(sound, filterFreq, filterRes);
		//sound = HPF.ar(sound, 200, 1);
		sound = FreeVerb.ar(sound, 0.3, reverbMix) *fade;

		//Out.ar(0, sound*fade);
		Out.ar(0, sound*fade);
	}).add;




// TEST
// a stereo version
SynthDef(\decay, { arg outBus = 0, effectBus, direct = 0.5, freq = 440;
    var source;
    // 1.0.rand2 returns a random number from -1 to 1, used here for a random pan
		source = Pan2.ar(Decay2.ar(Impulse.ar(Rand(0.3, 1), 0, 0.125), 0.3, 1));
    Out.ar(outBus, source * direct);
    Out.ar(effectBus, source * (1 - direct));
}).add;

SynthDef(\reverb, { arg outBus = 0, inBus;
    var input;
    input = In.ar(inBus, 2);
    16.do({ input = AllpassC.ar(input, 0.04, Rand(0.001,0.04), 3)});
    Out.ar(outBus, input);
}).add;
// END TEST

	s.sync;

	t = Toner.new(\samplePlayer1, \samplePlayer2, true); // run offline
	//t = Toner.new(\samplePlayer1, \samplePlayer2);
	t.loadPreprocessor();
}
);


s.options.sampleRate = 48000 // run and then reboot server (44100)


// "America" by Allen Ginsberg
// Used with Permission from the Estate of Allen Ginsberg. Abridged for performance.

America I have given you all and now I am nothing.
: America two dollars and twentyseven cents January 17, 1956.
: I can't stand my own mind.
: America when will we end the human war?
: Go fuck yourself with your atom bomb.
: I don't feel good don't bother me.
: I won't write my poem till I'm in my right mind.

// (3:30 - 4:15) - OP-Z ljud

// neutral, realization
:: America when will you be angelic?

// neutral, curiosity
:: When will you take off your clothes?

// curiosity, disapproval
:: When will you look at yourself through the grave?

// curiosity, confusion
:: When will you be worthy of your million Trotskyites?

// neutral, sadness
:: America why are your libraries full of tears?

// curiosity, neutral
:: America when will you send your eggs to India?

// annoyance, anger
:: I'm sick of your insane demands.

// neutral, annoyance
:: America after all it is you and I who are perfect not the next world.

// [...]

// desire, neutral
:: I’m trying to come to the point.

// disapproval, approval
:: I refuse to give up my obsession.

// annoyance, neutral
:: America stop pushing I know what I’m doing.

// realization, approval
:: America the plum blossoms are falling.







p.at(\p1).stop;
p.at(\p2).stop;
p.at(\p3).stop;
p.at(\p4).stop;
p.at(\p5).stop;
p.at(\p6).stop;
p.at(\p7).stop;




s.queryAllNodes


// TEST
(
~effects = Group.after(~group1);
~bus = Bus.audio(s, 2);

x = Synth(\reverb, [\inBus, ~currentSynths], ~effects);
y = Synth(\decay, [\effectBus, ~bus, \outBus, 0], ~effects);
)
(
// todo: failing to free effect group before adding a new one holds the first one around
~effects.free; // this frees their contents (x, y, z) as well
~bus.free;
)
// END TEST




/*
(
thisProcess.interpreter.preProcessor = {|codeBlock|
	c = codeBlock.split($ );
	d = codeBlock.replace("1 = ", replace:"");
	d.postln;

	if(codeBlock.beginsWith("1"), {
		"p = t.t(\""++d++"\", 1);"
	}, {
		"p = t.t(\""++d++"\");"
	});
};
)
