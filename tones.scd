(// with added speed modulator
b = Buffer.read(s, "/Users/roya/Documents/roya/Supercollider/samples/opz_flute.wav");
SynthDef(\modulatedSample, {|buf, speed = 1, modFreq = 2|
	var sig, lfo;
	lfo = LFSaw.kr(modFreq);
	sig = PlayBuf.ar(
		1,
		buf,
		speed * BufRateScale.kr(buf), //* lfo.linexp(-1, 1, 0.25, 4),
		loop: 1
	);
	sig = Pan2.ar(sig, lfo);
	Out.ar(0, sig);
}).add;
)


z = Synth(\modulatedSample, [\buf, b]);
z.set(\modFreq, 15);
z.free;

s.reboot

(
m = Tone.new("sad", \modulatedSample);
m.init;
)
m.stopSynth();

(
h = Tone.new("happy", \modulatedSample);
h.init;
)
h.stopSynth();
