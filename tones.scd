(
var samplePaths = [
    "/Users/roya/Documents/roya/Supercollider/KMH/tones/samples/buchla_beep.wav",
    "/Users/roya/Documents/roya/Supercollider/KMH/tones/samples/em.wav",
    "/Users/roya/Documents/roya/Supercollider/KMH/tones/samples/opz_flute.wav"
];

~buffers = Array.fill(samplePaths.size, { |index|
    var path = samplePaths[index];
    var buffer = File.exists(path).if {
        Buffer.read(s, path);
    } {
        "File not found: ".post; path.postln;
        nil;
    };
    buffer;
});
)

(
SynthDef(\modBuchlaBeep, { |buf, speed = 1, modFreq = 2|
    var env, sig, lfo;
	lfo = LFSaw.kr(modFreq);
    //env = EnvGen.kr(Env.perc, doneAction: 2);
	sig = PlayBuf.ar(
		1,
		~buffers[0].bufnum,
		speed * BufRateScale.kr(buf),
		loop: 1
	);
	sig = Pan2.ar(sig, lfo);

    Out.ar(0, sig);
}).add;

SynthDef(\modEm, {|buf, speed = 1, modFreq = 2|
	var sig, lfo;
	lfo = LFSaw.kr(modFreq);
	sig = PlayBuf.ar(
		1,
		~buffers[1].bufnum,
		speed * BufRateScale.kr(buf),
		loop: 1
	);
	sig = Pan2.ar(sig, lfo);
	Out.ar(0, sig);
}).add;

SynthDef(\modOpzFlute, {|buf, speed = 1, modFreq = 2|
	var sig, lfo;
	lfo = LFSaw.kr(modFreq);
	sig = PlayBuf.ar(
		1,
		~buffers[2].bufnum,
		speed * BufRateScale.kr(buf),
		loop: 1
	);
	sig = Pan2.ar(sig, lfo);
	Out.ar(0, sig);
}).add;
)


(
// test synth presets
var synth = Synth(\modBuchlaBeep);
synth.set(\rate, 1);
synth.set(\modFreq, 15);
synth.set(\speed, 1);
synth.set(\start, 0);
synth.set(\end, 1);
synth.set(\loop, 1);
)

// example playback
(
m = Tone.new("I am very happy", \modOpzFlute);
m.init;
)
m.stopSynth();

(
h = Tone.new("sad", \modBuchlaBeep);
h.init;
)
h.stopSynth();

(
x = Tone.new("so very sad", \modEm);
x.init;
)
x.stopSynth();
