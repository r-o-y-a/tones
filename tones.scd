s.options.sampleRate = 48000 // TODO: fix sample rate in buffer play
// http://172.20.10.2:8080/
// TEST for screen size
//df asdf asdf asdf asdf asdf adf adf adf adf asdf asdf asdf asdf asdfa.

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

		sound = CombL.ar(sound, maxdelaytime: delayMaxTime, delaytime: delayTime, decaytime: decayTime);

		sound = FreeVerb.ar(sound, 0.3, reverbMix) *fade;

		Out.ar(0, sound*fade);
	}).add;


	b = Buffer.read(s, "/Users/roya/Documents/roya/Supercollider/KMH/tones/static/tones_america_transition.wav");
	SynthDef(\usa, {|buf, amp = 0.2|
		var sig;
		sig = PlayBuf.ar(
			1,
			buf,
			1 * BufRateScale.kr(buf),
			loop: 1
		);
		Out.ar([0,1], sig*amp);
	}).add;


	s.sync;

	t = Toner.new(\samplePlayer1, \samplePlayer2, true);
	t.loadPreprocessor();
	t.getTones(".", 1, 0);
}
);


z = Synth(\usa, [\buf, b]);
z.set(\amp, 0.09);
z.free;




































p.at(\p1).stop;
p.at(\p2).stop;
p.at(\p3).stop;
p.at(\p4).stop;
p.at(\p5).stop;
p.at(\p6).stop;
p.at(\p7).stop;


s.queryAllNodes
