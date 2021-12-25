## Client-Facing API

The following endpoints are defined for triggering events through an HTTPS interface:


### Tool Status Events

| URL | Body | Action |
| :--- | :--- | :--- |
| `PUT` /mes/tool/`tool-id-string`/mock/status/up | `up-event` | Set tool status to UP |
| `PUT` /mes/tool/`tool-id-string`/mock/status/down | `down-event` | Set tool status to DOWN |


**up-event**

```json
{
	"time": iso-time-string,
}
```

**down-event**

```json
{
	"time": iso-time-string,
	"reason": reason-string,
}
```

**iso-time-string**


```
YYYY-MM-DDTHH:mm:ss:sssZ
```


```
YYYY: 	Year
MM: 	Month  01...12
DD: 	Day   01...31
HH: 	Hour  00...23
mm: 	Minute 00...59
ss: 	Second 00...59
sssZ: 	Millisecond 000Z...999Z
```


All iso-time-strings are given in UTC. Different to ISO 86001, all parts of the time string are required.

**reason-string**


| reason-string | Meaning |
| :--- | :--- |
| operator| operator interaction |
| material | missing material |
| auxiliary | missing auxiliary |
| process | process fault |
| equipment | equipment fault |
| unknown | unknown reason (default) |



### Parts Production Events
