CPPFLAGS += -D_GNU_SOURCE
CPPFLAGS += -DNDEBUG

CFLAGS += -O3 -fomit-frame-pointer -march=native
#CFLAGS += -ggdb -Wall

LDFLAGS += -Wl,-O1

CFLAGS += -std=c99

# libspinner
#CFLAGS += $(shell pkg-config --cflags libspinner)
#LDFLAGS += $(shell pkg-config --libs libspinner)

.PHONY: all clean

all: uvb

uvb: uvb.c

clean:
	$(RM) uvb
