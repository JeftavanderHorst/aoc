const std = @import("std");
const ArrayList = std.ArrayList;

// Parse file with comma-seperated inputs (such as 1, 2, 3)
pub fn parseFile(path: []const u8) !ArrayList(u64) {
    const file = try std.fs.cwd().openFile(path, .{ .read = true });
    var buffer: [4000]u8 = undefined;
    try file.seekTo(0);
    const bytes_read = try file.readAll(&buffer);

    const input = buffer[0..bytes_read];

    var numbers = ArrayList(u64).init(std.heap.page_allocator);

    var tokens = std.mem.tokenize(u8, input, ",");
    while (tokens.next()) |token| {
        try numbers.append(try std.fmt.parseUnsigned(u64, token, 10));
    }

    return numbers;
}

pub fn main() !void {
    var numbers = try parseFile("input.txt");

    var candidate: usize = 0;

    var best_candidate_index: u64 = std.math.maxInt(u64);
    var best_candidate_fuel: u64 = std.math.maxInt(u64);

    while (candidate <= 2000) {
        var total_cost: u64 = 0;
        for (numbers.items) | number| {
            total_cost += std.math.max(number, candidate) - std.math.min(number, candidate);
        }

        if (total_cost < best_candidate_fuel) {
            best_candidate_fuel = total_cost;
            best_candidate_index = candidate;
        }

        candidate += 1;
    }

    std.debug.print("Best candidate is {} with {} fuel\n", .{best_candidate_index, best_candidate_fuel});

    std.debug.print("Done!\n", .{});
}
